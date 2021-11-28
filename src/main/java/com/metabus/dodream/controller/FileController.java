package com.metabus.dodream.controller;

import com.google.gson.JsonObject;
import com.metabus.dodream.config.path.PathDeterminant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/dodream")
@RequiredArgsConstructor
@Slf4j
public class FileController {


    PathDeterminant pathDeterminant = new PathDeterminant();
    private String DIRECTORY = pathDeterminant.getOS_TYPE();




    @PostMapping(value = "/deprecated", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> upload(@RequestBody MultipartFile file) throws IOException, JSONException {
        String fileName = file.getOriginalFilename();

        JsonObject param = new JsonObject();
        File mkdir = new File(DIRECTORY);
        if(!mkdir.exists()){
            mkdir.mkdir();
        }
        if(!file.getOriginalFilename().isEmpty()) {

            file.transferTo(new File(DIRECTORY+"/"+fileName));
            param.addProperty("msg", "File uploaded successfully.");
            param.addProperty("fileName", fileName);
        }else {
            param.addProperty("msg", "Please select a valid mediaFile..");
        }

        HttpHeaders response_header = new HttpHeaders();
        response_header.set("content-type", "application/json");

        JSONObject response = new JSONObject();
        response.put("msg", param.get("msg"));
        response.put("msg", param.get("fileName"));

        return new ResponseEntity<>(response.toString(),response_header, HttpStatus.OK);

    }


    @PostMapping("/upload")
    public ResponseEntity<String> test(@RequestParam("file") MultipartFile file, @RequestParam("wallet") String wallet,
                                       @RequestParam("name") String name, @RequestParam("id") String id ) throws IOException {
        String fileName = file.getOriginalFilename();
        File mkdir = new File(DIRECTORY);
        if(!mkdir.exists()){
            mkdir.mkdir();
        }
        JsonObject param = new JsonObject();
        HttpHeaders response_header = new HttpHeaders();
        response_header.set("content-type", "application/json");
        if(!file.getOriginalFilename().isEmpty()) {
            file.transferTo(new File(DIRECTORY, fileName));
            param.addProperty("result",true);
            param.addProperty("msg", "File uploaded successfully.");
            param.addProperty("fileName", fileName);
            param.addProperty("wallet", wallet);
            param.addProperty("name", name);
            param.addProperty("id", id);


        }else {
            param.addProperty("msg", "Please select a valid mediaFile..");
        }

        return new ResponseEntity<>(param.toString(),response_header, HttpStatus.OK);
    }

}
