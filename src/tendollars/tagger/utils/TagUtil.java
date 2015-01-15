package tendollars.tagger.utils;

import javafx.collections.ObservableList;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import tendollars.tagger.model.FileInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by yukai on 2015/1/14.
 */
public class TagUtil {

    static boolean isValid(String tagString) {
        return true;
    }

    static public Collection<File> scanFile(File file) {

        Collection<File> files = FileUtils.listFiles(
                file,
                new RegexFileFilter("^(.*?)"),
                DirectoryFileFilter.DIRECTORY
        );

        return files;
    }

    static public ArrayList<String> uniqueArray(String s) {
        ArrayList<String> tags = new ArrayList<String>(Arrays.asList(s.split("\\s+")));
        HashSet<String> hs = new HashSet<>();
        hs.addAll(tags);
        tags.clear();
        tags.addAll(hs);

        return tags;
    }

    static public ArrayList<String> uniqueArrayList(ArrayList<String> arr) {
        HashSet<String> hs = new HashSet<>();
        hs.addAll(arr);
        arr.clear();
        arr.addAll(hs);
        return arr;
    }
    static public ObservableList<FileInfo> uniqueFileInfos(ObservableList<FileInfo> arr) {
        HashSet<FileInfo> hs = new HashSet<>();
        hs.addAll(arr);
        arr.clear();
        arr.addAll(hs);
        return arr;
    }

}
