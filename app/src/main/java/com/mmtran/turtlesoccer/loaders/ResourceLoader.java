package com.mmtran.turtlesoccer.loaders;

import android.content.res.Resources;

import com.google.gson.Gson;
import com.mmtran.turtlesoccer.R;
import com.mmtran.turtlesoccer.models.Nation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ResourceLoader {

    private Resources resources;

    private List<Nation> nationList;

    public ResourceLoader(Resources resources) {
        this.resources = resources;
    }

    public List<Nation> getNations() {

        InputStream XmlFileInputStream = resources.openRawResource(R.raw.nations);
        String jsonString = readTextFile(XmlFileInputStream);
        Gson gson = new Gson();

        Nation[] nations =  gson.fromJson(jsonString, Nation[].class);
        nationList = Arrays.asList(nations);

        return nationList;
    }

    public List<Nation> getActiveNations() {
        return getNations().stream().filter(nation ->
                nation.getParentNationId().isEmpty() && nation.getConfederationId() != null && !nation.getConfederationId().isEmpty())
                .collect(Collectors.toList());
    }

    public String readTextFile(InputStream inputStream) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte buf[] = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {

        }
        return outputStream.toString();
    }
}
