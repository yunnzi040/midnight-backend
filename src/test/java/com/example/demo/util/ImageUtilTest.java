package com.example.demo.util;

import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

class ImageUtilTest {

    /**
     * 간단한 테스트 이미지 생성
     */
    private BufferedImage createTestImage(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        
        // 배경을 흰색으로 채우기
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        
        // 간단한 도형 그리기
        g2d.setColor(Color.RED);
        g2d.fillOval(width/4, height/4, width/2, height/2);
        
        g2d.dispose();
        return image;
    }

    @Test
    void testConvertBufferedImageToByteArray() throws IOException {
        // 테스트 이미지 생성
        BufferedImage testImage = createTestImage(100, 100);
        
        // 바이트 배열로 변환
        byte[] imageBytes = ImageUtil.convertBufferedImageToByteArray(testImage, "png");
        
        // 변환된 바이트 배열 검증
        assertNotNull(imageBytes);
        assertTrue(imageBytes.length > 0);
        
        // 바이트 배열을 다시 이미지로 변환하여 검증
        BufferedImage reconstructedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
        assertNotNull(reconstructedImage);
        assertEquals(testImage.getWidth(), reconstructedImage.getWidth());
        assertEquals(testImage.getHeight(), reconstructedImage.getHeight());
    }

    @Test
    void testBase64Conversion() {
        // 더미 이미지 생성
        byte[] dummyImage = ImageUtil.createDummyImage();
        
        // Base64로 변환
        String base64Image = ImageUtil.convertByteArrayToBase64(dummyImage, false, null);
        
        // 다시 바이트 배열로 변환
        byte[] reconstructedBytes = ImageUtil.convertBase64ToByteArray(base64Image);
        
        // 원본과 비교
        assertArrayEquals(dummyImage, reconstructedBytes);
    }

    @Test
    void testCreateDummyImage() {
        byte[] dummyImage = ImageUtil.createDummyImage();
        assertNotNull(dummyImage);
        assertTrue(dummyImage.length > 0);
    }
    
    // 주의: 이 테스트는 실제 파일이 있어야 실행됩니다. 필요에 따라 수정하세요.
    //@Test
    void testFileConversion() throws IOException {
        // 테스트 이미지 파일 경로 (필요에 따라 수정)
        String testImagePath = "src/test/resources/test-image.png";
        
        // 테스트 이미지가 없는 경우 생성
        Path path = Paths.get(testImagePath);
        if (!Files.exists(path)) {
            Files.createDirectories(path.getParent());
            BufferedImage testImage = createTestImage(100, 100);
            ImageIO.write(testImage, "png", path.toFile());
        }
        
        // 파일에서 바이트 배열로 변환
        byte[] imageBytes = ImageUtil.convertImageFileToByteArray(testImagePath);
        
        // 검증
        assertNotNull(imageBytes);
        assertTrue(imageBytes.length > 0);
    }
}
