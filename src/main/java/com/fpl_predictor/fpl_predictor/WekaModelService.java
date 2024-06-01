package com.fpl_predictor.fpl_predictor;

import org.springframework.stereotype.Service;

import weka.classifiers.Classifier;
import weka.core.Instances;
import weka.core.SerializationHelper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class WekaModelService {

    public Classifier GK_Model;
    public Classifier DEF_Model;
    public Classifier MID_Model;
    public Classifier FWD_Model;

    // Load pre-trained WEKA models from resources
    public WekaModelService() throws Exception {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("models/GK_CS_NB_22_80.model")) {
            this.GK_Model = (Classifier) SerializationHelper.read(inputStream);
        }

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("models/DEF_CS_BN_22_74.model")) {
            this.DEF_Model = (Classifier) SerializationHelper.read(inputStream);
        }

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("models/MID_CS_BN_28_64.model")) {
            this.MID_Model = (Classifier) SerializationHelper.read(inputStream);
        }

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("models/FWD_CS_BN_22_70.model")) {
            this.FWD_Model = (Classifier) SerializationHelper.read(inputStream);
        }
    }

    
    public List<PredictionResponse> predict(String arffData, Classifier model) throws Exception {
        Instances data = new Instances(new BufferedReader(new StringReader(arffData)));
        data.setClassIndex(data.numAttributes() - 1);

        List<PredictionResponse> predictions = new ArrayList<>();
        for (int i = 0; i < data.numInstances(); i++) {
            double label = model.classifyInstance(data.instance(i));
            double[] distribution = model.distributionForInstance(data.instance(i));
            predictions.add(new PredictionResponse(i, label, distribution));
        }

        return predictions;
    }
}
