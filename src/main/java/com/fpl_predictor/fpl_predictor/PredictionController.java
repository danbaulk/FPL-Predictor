package com.fpl_predictor.fpl_predictor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.google.gson.Gson;

import weka.core.Attribute;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

@RestController
@RequestMapping("/fplai")
public class PredictionController {

    @Autowired
    private WekaModelService wekaModelService;

    @PostMapping("/predict/gk")
    public String predictGK(@RequestBody String arffData) throws Exception {
        try {
            List<PredictionResponse> predictions = wekaModelService.predict(arffData, wekaModelService.GK_Model);
            return toJson(predictions);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    @PostMapping("/predict/def")
    public String predictDEF(@RequestBody String arffData) throws Exception {
        try {
            List<PredictionResponse> predictions = wekaModelService.predict(arffData, wekaModelService.DEF_Model);
            return toJson(predictions);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    @PostMapping("/predict/mid")
    public String predictMID(@RequestBody String arffData) throws Exception {
        try {
            List<PredictionResponse> predictions = wekaModelService.predict(arffData, wekaModelService.MID_Model);
            return toJson(predictions);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    @PostMapping("/predict/fwd")
    public String predictFWD(@RequestBody String arffData) throws Exception {
        try {
            List<PredictionResponse> predictions = wekaModelService.predict(arffData, wekaModelService.FWD_Model);
            return toJson(predictions);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    // Convert list of predictions to JSON
    public String toJson(List<PredictionResponse> predictions) {
        return new Gson().toJson(predictions);
    }

    @PostMapping("/data/convert")
    public String convertCsvToArff(@RequestBody String csvData) throws Exception {
        try {
            // load the csv data
            CSVLoader loader = new CSVLoader();
            InputStream inputStream = new ByteArrayInputStream(csvData.getBytes());
            loader.setSource(inputStream);
            Instances data = loader.getDataSet();

            // Define class attribute
            if (data.attribute("class") != null) {
                data.deleteAttributeAt(data.attribute("class").index());
            }
            List<String> classValues = new ArrayList<>();
            classValues.add("return");
            classValues.add("no");
            Attribute classAttribute = new Attribute("class", classValues);
            data.insertAttributeAt(classAttribute, data.numAttributes());
            data.setClassIndex(data.numAttributes() - 1);
    
            // convert to arff
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ArffSaver saver = new ArffSaver();
            saver.setInstances(data);
            saver.setDestination(outputStream);
            saver.writeBatch();
    
            return outputStream.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

}
