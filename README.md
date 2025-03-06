# FPL-Predictor

This is the intermediary JAVA service which will be running the WEKA models, providing an interface for the backend Golang API to utilise its predictions.

Using Spring Boot and Maven.

## Usage
- Install Docker
- Clone this repo
- cd into the root of this repo
- Build a local image
```bash
docker build -t fpl-predictor .
```
- Run the image as a container
```bash
docker run -p 8080:8080 fpl-predictor
```
- the fplai/data/convert endpoint can be used to convert a given csv in the request body to ARFF for the preciction models
- the fplai/predict/{pos} endpoint can be used to make predictions on whether a player will score highly in the upcoming gameweek based on the provided ARFF data in the request body, along with a confidence score. {pos} can be one of "gk, def, mid, fwd".
