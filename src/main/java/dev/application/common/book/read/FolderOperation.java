package dev.application.common.book.read;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;

/**
 * フォルダ内のファイル操作クラス
 * @author shiraishitoshio
 *
 */
public class FolderOperation {

	/** 探索件数 */
	@Value("${bmbusiness.findbookcounter:1}")
	private static int FIND_COUNTER = 1000;

	/**
	 * テキストファイルが指定されたパスに存在するかどうかをチェック
	 *
	 * @param filePath チェックするファイルのパス
	 * @param extension 拡張子
	 * @return ファイルが存在する場合は true, 存在しない場合は false
	 */
	public static boolean isTxtFileExist(String filePath, String extension) {
		File file = new File(filePath);

		// ファイルが存在するか、かつ拡張子が .txt または .xls の場合
		if (file.exists() && (file.getName().endsWith(extension))) {
			return true;
		}
		return false;
	}

	/**
	 * ファイルをコピーする
	 * @param origin_path XXXX/<<バージョン番号>/XXXXX.txtになっていること想定
	 * @param copy_path XXXX/<<バージョン番号+1>/XXXXX.txtになっていること想定
	 */
	public static void fileCopy(String origin_path, String copy_path) {
		Path p1 = Paths.get(origin_path);
		Path p2 = Paths.get(copy_path);

		try {
			Files.copy(p1, p2);
		} catch (IOException e) {
			return;
		}
	}

	/**
	 * フォルダを作成する
	 * @param origin_path
	 */
	public static void makeFolder(String origin_path) {
		String[] path_split = null;
		if (origin_path.contains("/")) {
			path_split = origin_path.split("/");
		}

		// 再帰的に作成する
		if (path_split != null && path_split.length > 0) {
			String allpath = "";
			for (String path : path_split) {
				if (allpath != "") {
					allpath += "/";
				}
				allpath += path;
				File directory = new File(allpath);

				if (!directory.exists()) {
					if (directory.mkdirs()) {
						//System.out.println("Directory created: " + allpath);
					} else {
						//System.out.println("Failed to create directory: " + allpath);
					}
				} else {
					//System.out.println("Directory already exists: " + allpath);
				}
			}
		}
	}

	/**
	 * バージョン番号に該当する値を1増やす
	 * @param originalPath XXXX/<<バージョン番号>/XXXXX.txtになっていること前提
	 */
	public static String convertPath(String originalPath) {
		String[] path_split = null;
		if (originalPath.contains("/")) {
			path_split = originalPath.split("/");
		}
		String ver = path_split[path_split.length - 2];
		String next_ver = String.valueOf(Integer.parseInt(ver) + 1);

		// 新しいフォルダパスを取得
		String newPath = getUpdatedPath(originalPath, ver, next_ver);

		return newPath;
	}

	/**
	 * 指定されたパスの一部を置換して新しいパスを返す
	 * @param originalPath 変更前のパス
	 * @param oldSegment 変更対象の文字列
	 * @param newSegment 変更後の文字列
	 * @return 変更後のパス名
	 */
	private static String getUpdatedPath(String originalPath, String oldSegment, String newSegment) {
		return originalPath.replace("/" + oldSegment + "/", "/" + newSegment + "/");
	}

	/**
	 * 最大のバージョン情報を持つパスにバージョン情報を増加させたパスを取得
	 * @param baseDirectory XXXX/<<バージョン番号>/XXXXX.txtになっていること前提
	 * @return
	 * @throws IOException
	 */
	public static String getNextVersionPath(String baseDirectory) throws IOException {
		String[] path_split = null;
		if (baseDirectory.contains("/")) {
			path_split = baseDirectory.split("/");
		}

		String allpath = "";
		int count = 0;
		for (String path : path_split) {
			if (allpath != "") {
				allpath += "/";
			}
			if (count == path_split.length - 1)
				break;
			allpath += path;
			count++;
		}

		// ファイル名保存
		String filename = path_split[path_split.length - 1];

		// パスが存在しなければbaseDirectoryをreturn
		File file = new File(allpath);
		if (!file.exists()) {
			return baseDirectory;
		}

		// バージョン情報を含まないパスを取得する
		String[] path_sub_split = allpath.split("/");
		String allSubpath = "";
		int subCount = 0;
		for (String path : path_split) {
			if (allSubpath != "") {
				allSubpath += "/";
			}
			if (subCount == path_sub_split.length - 1)
				break;
			allSubpath += path;
			subCount++;
		}

		List<Path> bookPathPathList = new ArrayList<>();
		List<String> bookPathSortList = new ArrayList<>();
		Files.walk(Paths.get(allSubpath))
				.filter(Files::isRegularFile) // ファイルのみ
				.filter(pathStr -> pathStr.toString().endsWith(".txt"))
				.forEach(bookPathPathList::add);
		for (Path pathStr : bookPathPathList) {
			if (pathStr.toString().contains("breakfile")) {
				continue;
			}
			String convString = pathStr.toString();
			convString = convString.replace(allSubpath, "");
			bookPathSortList.add(convString.split("/")[0]);
		}
		// ソートしてバージョン情報の数字を+1する
		Collections.sort(bookPathSortList, Comparator.comparingInt(Integer::parseInt));
		return allSubpath +
				String.valueOf(Integer.parseInt(bookPathSortList.get(bookPathSortList.size() - 1)) + 1);
	}

	/**
	 * 最大のバージョン情報を持つパスにバージョン情報を増加させたパスを取得
	 * @param baseDirectory XXXX/<<バージョン番号>/XXXXX.txtになっていること前提
	 * @return
	 * @throws IOException
	 */
	public static String getMaxVersionPath(String baseDirectory) throws IOException {
		List<Path> bookPathPathList = new ArrayList<>();
		List<String> bookPathSortList = new ArrayList<>();
		Files.walk(Paths.get(baseDirectory))
				.filter(Files::isRegularFile) // ファイルのみ
				.filter(pathStr -> pathStr.toString().endsWith(".txt"))
				.forEach(bookPathPathList::add);
		for (Path pathStr : bookPathPathList) {
			if (pathStr.toString().contains("breakfile")) {
				continue;
			}
			String convString = pathStr.toString();
			convString = convString.replace(baseDirectory, "");
			bookPathSortList.add(convString.split("/")[0]);
		}
		// ソートしてバージョン情報の数字の最大値を取得する
		Collections.sort(bookPathSortList, Comparator.comparingInt(Integer::parseInt));
		if (bookPathSortList.isEmpty()) {
			return "1";
		}
		return bookPathSortList.get(bookPathSortList.size() - 1);
	}

}
