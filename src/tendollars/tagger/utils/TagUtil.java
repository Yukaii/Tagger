package tendollars.tagger.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;

import java.io.File;
import java.util.Collection;

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
}
