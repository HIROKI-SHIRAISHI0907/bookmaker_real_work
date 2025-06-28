package dev.application;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FileUtils;

public class TestFileUtils {

    /**
     * 指定されたリソースファイルをコピーします。
     *
     * @param resourcePath リソースフォルダ内のパス（例: "test/sample.txt"）
     * @param destinationDir コピー先のディレクトリ
     * @param destinationPath コピー先のパス
     * @throws IOException ファイル操作時の例外
     */
    public static void copyResourceTo(String resourcePath, String destinationDir, String destinationPath) throws IOException {
        Files.createDirectories(Paths.get(destinationDir)); // コピー先のディレクトリを作成
        Files.copy(Paths.get(resourcePath), Paths.get(destinationPath), StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * 指定されたリソースファイルを削除します。
     *
     * @param destinationPath コピー先のパス
     * @throws IOException ファイル操作時の例外
     */
    public static void deleteResourceTo(String destinationPath) throws IOException {
        FileUtils.forceDelete(new File(destinationPath)); // コピー先のディレクトリを削除
    }
}