package uk.ac.ebi.pride.archive.repo.file.service;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Daniel Rios
 * @author Jose A. Dianes
 * @version $Id$
 */
@Deprecated
@Service
public class FileUtils {

    /**
     * Stream a given file to HttpServletResponse
     */
    public void streamFile(HttpServletResponse response, File fileToStream) throws IOException {
        FileInputStream fis = new FileInputStream(fileToStream);

        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileToStream.getName() + "\"");

        if (fileToStream.getName().endsWith(".gz")) {
            response.setContentType("application/x-gzip");
        } else {
            response.setContentType("text/plain; charset=utf-8");
        }

        IOUtils.copy(fis, response.getOutputStream());

        response.flushBuffer();
    }


    /**
     * Find the file to be streamed to the client
     */
    public File findFileToStream(FileSummary fileSummary) throws FileNotFoundException {
        File fileToStream;

        if (fileSummary != null) {
            fileToStream = new File(fileSummary.getFilePath());
            if (!fileToStream.exists()) {
                throw new FileNotFoundException("Failed to find the file to stream : " + fileToStream.getAbsolutePath());
            }
        } else {
            throw new FileNotFoundException("Failed to find the file to stream");
        }

        return fileToStream;
    }

}
