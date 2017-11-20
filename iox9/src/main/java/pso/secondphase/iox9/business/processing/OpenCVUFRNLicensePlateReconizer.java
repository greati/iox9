/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pso.secondphase.iox9.business.processing;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import model.CcaOpenCvCharacterSegmenter;
import model.HaarOpenCvPlateExtractor;
import model.OpenCvAlpr;
import model.PixelsKNNOCR;
import model.PixelsOpticalCharRecognizer;
import model.Plate;
import nu.pattern.OpenCV;
import static org.opencv.core.CvType.CV_8UC3;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.objdetect.CascadeClassifier;

/**
 *
 * @author vitorgreati
 */
public class OpenCVUFRNLicensePlateReconizer implements EntityRecognizer<Image> {
    private OpenCvAlpr alpr;
    
    static{ OpenCV.loadLocally(); }

    
    public OpenCVUFRNLicensePlateReconizer() {
        Path currentRelativePath = Paths.get("");
        String path = currentRelativePath.toAbsolutePath().toString();
        
        CascadeClassifier cc = new CascadeClassifier(path + "/src/main/resources/xml/filter_br_plates_openalpr.xml");
        HaarOpenCvPlateExtractor haarOpenCvPlateExtractor = new HaarOpenCvPlateExtractor(cc);
        
        CcaOpenCvCharacterSegmenter ccaOpenCvCharacterSegmenter = new CcaOpenCvCharacterSegmenter();
        PixelsOpticalCharRecognizer pixelOCR = new PixelsKNNOCR(15,20,10);
        pixelOCR.load(path + "/src/main/resources/json/allchars_cca_60_letters.json");
        pixelOCR.load(path + "/src/main/resources/json/allchars_cca_60_numbers.json");
        this.alpr = new OpenCvAlpr(haarOpenCvPlateExtractor, ccaOpenCvCharacterSegmenter, pixelOCR);
    }
    
    @Override
    public String recognize(Image img) {
        
        if (img == null)
            return null;
        
        // Convert to OpenCV type
        byte[] pixels = ((DataBufferByte) ((BufferedImage) img).getRaster().getDataBuffer()).getData();
        Mat matImage = new Mat(new Size(((BufferedImage) img).getWidth(), ((BufferedImage) img).getHeight()), CV_8UC3);
 
        matImage.put(0, 0, pixels);
        
        // Uses ALPR
        List<Plate> plates = alpr.alpr(matImage, "LLLNNNN");
        
        if (plates.isEmpty())
            return null;
        
        List<String> stringPlates = new ArrayList<>();
        for (Plate p : plates) {
            StringBuilder builder = new StringBuilder(p.getCharacters().size());
            for (Character ch: p.getCharacters())
                builder.append(ch);
            stringPlates.add(builder.toString());
        }
        
        return stringPlates.get(0);
    }
    
}
