package ua.alexd.excelUtils.imports;

import ua.alexd.domain.ShopDomain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Importer {
    public List<? extends ShopDomain> importFile(String uploadedFilePath) throws IOException, IllegalArgumentException {
        return new ArrayList<>();
    }

    protected void nullExtractedDomains(Object... extractedDomains) {
        Arrays.fill(extractedDomains, null);
    }
}