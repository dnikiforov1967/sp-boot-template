/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.root.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author dima
 */
public class FileAccessTest {
    
    public FileAccessTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void assessFile() throws FileNotFoundException, IOException {
        File file = new File("localaccess.txt");
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        raf.writeBytes("abcdefghjiklmnoprqstuvwxyz");
        raf.seek(6);
        byte[] bytes = new byte[3];
        raf.read(bytes);
        assertEquals("ghj",new String(bytes));
        raf.close();
    }
    
}
