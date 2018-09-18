package org.usfirst.frc.falcons6443.robot.utilities;

import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class FileStorage {
    private Path rioPath = Paths.get("/home");

    private FileStore getFileStore(Path pathName) throws Exception{
        return Files.getFileStore(pathName);
    }

    //Gigabytes
    public double getTotalSpace() throws Exception{
        return getFileStore(rioPath).getTotalSpace() / 1073741824.0;//1073741824
    }

    public double getUsableSpace() throws Exception{
        return getFileStore(rioPath).getUsableSpace() / 1073741824.0;
    }

    public double getFilledSpace() throws Exception{
        return getTotalSpace() - getUsableSpace();
    }
}