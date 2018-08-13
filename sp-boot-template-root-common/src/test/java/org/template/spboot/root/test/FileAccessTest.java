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
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

class RegionWrite implements Runnable {

	int pos;
	String value;

	public RegionWrite(int pos, String value) {
		this.pos = pos;
		this.value = value;
	}
	
	@Override
	public void run() {
        File file = new File("regionaccess.txt");
        RandomAccessFile raf;
		try {
			raf = new RandomAccessFile(file, "rw");
			final FileChannel channel = raf.getChannel();
			final MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, pos, 10);
			map.put(value.getBytes());
			map.force();
		    raf.close();	
		} catch (FileNotFoundException ex) {
			Logger.getLogger(RegionWrite.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(RegionWrite.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}


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
		file.delete();
    }
    
    @Test
    public void assessRegionFile() throws FileNotFoundException, IOException, InterruptedException {
        File file = new File("regionaccess.txt");
		file.delete();
		final RegionWrite regionWriteA = new RegionWrite(10,"1234567");
		final RegionWrite regionWriteB = new RegionWrite(1,"ABCDEFGH");
		final Thread threadA = new Thread(regionWriteA);
		final Thread threadB = new Thread(regionWriteB);
		threadA.start();
		threadA.join();
		threadB.start();
		threadB.join();
		file = new File("regionaccess.txt");
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
		byte[] bytes = new byte[64];
		final int read = raf.read(bytes);
		System.out.println("I have red "+read+" bytes");
		for(byte b : bytes) {
			System.out.println(b);
		}
		raf.close();
	}	
	
}
