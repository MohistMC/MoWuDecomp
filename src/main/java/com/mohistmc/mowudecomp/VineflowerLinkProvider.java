package com.mohistmc.mowudecomp;


import com.strobel.assembler.metadata.TypeDefinition;
import org.jetbrains.java.decompiler.main.Fernflower;
import org.jetbrains.java.decompiler.main.decompiler.PrintStreamLogger;
import org.jetbrains.java.decompiler.main.extern.IContextSource;
import org.jetbrains.java.decompiler.main.extern.IResultSaver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.jar.Manifest;

/**
 * @author Mgazul by MohistMC
 * @date 2023/4/13 23:50:45
 */
public class VineflowerLinkProvider implements LinkProvider, IContextSource, IResultSaver {

    private TypeDefinition type;

    private byte[] bytecode;

    private String content;

    @Override
    public String getTextContent() {
        return content;
    }

    @Override
    public void setType(TypeDefinition type, Model model) {
        this.type = type;
        bytecode = ProcyonUtils.getContent(type, model);
    }

    @Override
    public void generateContent() {
        var fernflower = new Fernflower(this, Collections.emptyMap(), new PrintStreamLogger(System.out));
        File singletonJar = null;
        try {
            singletonJar = ProcyonUtils.createSingletonTempJar(type.getInternalName() + ".class", bytecode);
            fernflower.addSource(singletonJar);
            fernflower.decompileContext();
        } catch (Exception e) {
            MoWuDecomp.showExceptionDialog("Exception!", e);
        } finally {
            fernflower.clearContext();
            if (singletonJar != null)
                singletonJar.delete();
        }
    }

    @Override
    public void saveClassFile(String path, String qualifiedName, String entryName, String content, int[] mapping) {
        this.content = content;
    }

    @Override
    public void saveClassEntry(String path, String archiveName, String qualifiedName, String entryName, String content) {
        this.content = content;
    }

    //<editor-fold desc="Unneeded methods" defaultstate="collapsed">

    @Override
    public void processLinks() {
    }

    @Override
    public Map<String, Selection> getDefinitionToSelectionMap() {
        return Collections.emptyMap();
    }

    @Override
    public Map<String, Set<Selection>> getReferenceToSelectionsMap() {
        return Collections.emptyMap();
    }

    @Override
    public boolean isLinkNavigable(String uniqueStr) {
        return false;
    }

    @Override
    public String getLinkDescription(String uniqueStr) {
        return "";
    }

    @Override
    public void saveFolder(String path) {

    }

    @Override
    public void copyFile(String source, String path, String entryName) {

    }

    @Override
    public void createArchive(String path, String archiveName, Manifest manifest) {

    }

    @Override
    public void saveDirEntry(String path, String archiveName, String entryName) {

    }

    @Override
    public void copyEntry(String source, String path, String archiveName, String entry) {

    }

    @Override
    public void closeArchive(String path, String archiveName) {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Entries getEntries() {
        return null;
    }

    @Override
    public InputStream getInputStream(String resource) throws IOException {
        return null;
    }
    //</editor-fold>
}
