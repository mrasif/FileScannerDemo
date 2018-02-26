package in.mrasif.apps.filescannerdemo.utils;

import java.io.File;

/**
 * Created by asif on 26/2/18.
 */

public class FileType {
    private File file;
    private String extension;

    public FileType(File file) {
        this.file = file;

        this.extension = FileManager.getExtension(file.getName());
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    @Override
    public String toString() {
        return "FileType{" +
                "name='" + file + '\'' +
                ", extension='" + extension + '\'' +
                '}';
    }
}
