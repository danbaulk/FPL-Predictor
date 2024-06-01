package com.fpl_predictor.fpl_predictor;

public class PredictionResponse {
    private int instance;
    private double predictedClass;
    private double[] confidenceScores;

    public PredictionResponse(int instance, double predictedClass, double[] confidenceScores) {
        this.instance = instance;
        this.predictedClass = predictedClass;
        this.confidenceScores = confidenceScores;
    }
}
