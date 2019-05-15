package org.arxing.core;

import com.annimon.stream.Stream;

import org.arxing.core.utils.FileUtils;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        // write your code here
        String[] paths = {
                "C:/Users/arxing/Desktop/auto-template/test/folder1/ic_cover.png",
                "C:/Users/arxing/Desktop/auto-template/test/folder2/ic_index_speaker.png",
                "C:/Users/arxing/Desktop/auto-template/test/ic_about_icon.png",
                "C:/Users/arxing/Desktop/auto-template/test/ic_post_like.png",
                "W:/aa/bb/aa.png"
        };
        String output = "C:/Users/arxing/Desktop/auto-template/test/aa.zip";
        FileUtils.zip(Stream.of(paths).map(File::new).toList(), new File(output));
    }
}
