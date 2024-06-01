package com.fpl_predictor.fpl_predictor;

import org.springframework.stereotype.Service;
import weka.classifiers.Classifier;
import weka.core.Instances;
import weka.core.SerializationHelper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.StringReader;

@Service
public class WekaModelService {

    private Classifier GK_Model;
    private Classifier DEF_Model;
    private Classifier MID_Model;
    private Classifier FWD_Model;

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

    public String predictGK(String arffData) throws Exception {
        return predict(arffData, GK_Model);
    }
    
    public String predictDEF(String arffData) throws Exception {
        return predict(arffData, DEF_Model);
    }
    
    public String predictMID(String arffData) throws Exception {
        return predict(arffData, MID_Model);
    }
    
    public String predictFWD(String arffData) throws Exception {
        return predict(arffData, FWD_Model);
    }

    private String predict(String arffData, Classifier model) throws Exception {
        // Read the arff data from the request body string and configure the class
        Instances data = new Instances(new BufferedReader(new StringReader(arffData)));
        data.setClassIndex(data.numAttributes() - 1);
    
        // Make predictions
        StringBuilder predictions = new StringBuilder();
        for (int i = 0; i < data.numInstances(); i++) {
            double label = model.classifyInstance(data.instance(i));
            double[] distribution = model.distributionForInstance(data.instance(i));
    
            predictions.append("Instance ").append(i).append(": ");
            predictions.append("Predicted class: ").append(label).append(", ");
            predictions.append("Confidence scores: [");
            for (int j = 0; j < distribution.length; j++) {
                predictions.append(j).append(": ").append(distribution[j]);
                if (j < distribution.length - 1) {
                    predictions.append(", ");
                }
            }
            predictions.append("]\n");
        }
    
        return predictions.toString();
    }
}
