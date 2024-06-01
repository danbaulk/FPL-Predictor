package com.fpl_predictor.fpl_predictor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fplai")
public class PredictionController {

    @Autowired
    private WekaModelService wekaModelService;

    @PostMapping("/predict/gk")
    public String predictGK(@RequestBody String arffData) {
        try {
            return wekaModelService.predictGK(arffData);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    @PostMapping("/predict/def")
    public String predictDEF(@RequestBody String arffData) {
        try {
            return wekaModelService.predictDEF(arffData);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    @PostMapping("/predict/mid")
    public String predictMID(@RequestBody String arffData) {
        try {
            return wekaModelService.predictMID(arffData);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    @PostMapping("/predict/fwd")
    public String predictFWD(@RequestBody String arffData) {
        try {
            return wekaModelService.predictFWD(arffData);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}
