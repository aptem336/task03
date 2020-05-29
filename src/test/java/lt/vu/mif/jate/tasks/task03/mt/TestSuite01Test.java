package lt.vu.mif.jate.tasks.task03.mt;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import lt.vu.mif.jate.tasks.task03.mt.tool.ClientTest;
import static junit.framework.TestCase.fail;
import static junit.framework.TestCase.assertFalse;
import lt.vu.mif.jate.tasks.task03.mt.client.Client;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Simple Client class test. Single connection, single thread.
 * No worries - this test suite must pass (in the beginning...)!
 * 
 * @author valdo
 */
@RunWith(JUnit4.class)
public class TestSuite01Test implements ClientTest {

    /**
     * Passing all the tests.
     */
    @Test
    public void ClientTest() {
        try (Client c = new Client(SERVER_ADDR)) {
            
            //Thread.sleep(60000);
            test("Simple test", c);

        } catch (Exception ex) {
            //ex.printStackTrace(System.err);
            fail(ex.getMessage());
        }
    }

    /**
     * Check that synchronized is not used anywhere!
     *
     * @throws java.io.IOException on error
     */
    @Test
    public void SynchronizedTest() throws IOException {

        SyncFinder finder = new SyncFinder();
        Files.walkFileTree(Paths.get("src/main/java"), finder);

        assertFalse(String.format("Synchronized method found in %s", finder.syncFile), finder.syncFound);

    }

    private static class SyncFinder extends SimpleFileVisitor<Path> {

        private boolean syncFound = false;
        private Path syncFile = null;
        
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            if (attrs.isRegularFile() && file.getFileName().toString().endsWith(".java")) {
                String contents = new String(Files.readAllBytes(file), Charset.defaultCharset());
                if (contents.contains("synchronized")) {
                    syncFound = true;
                    syncFile = file;
                }
            }
            return FileVisitResult.CONTINUE;
        }

    }

}
