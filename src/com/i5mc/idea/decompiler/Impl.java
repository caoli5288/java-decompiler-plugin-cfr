package com.i5mc.idea.decompiler;

import com.google.common.collect.ImmutableMap;
import org.benf.cfr.reader.PluginRunner;

import java.util.Map;

/**
 * Created on 17-3-8.
 */
public interface Impl {

    Map<String, String> OPTION = ImmutableMap.of("hideutf", "false");

    String decompile(String path);

    static String fuck(String path) {
        PluginRunner r = new PluginRunner(OPTION);
        r.addJarPath(path);
        return r.getDecompilationFor(path);
    }

    static String clazz(String path) {
        int i = path.lastIndexOf("!/");// filesystem path can contain ! but java can not
        if (i == -1) throw new IllegalArgumentException();
        PluginRunner r = new PluginRunner(OPTION);
        String head = path.substring(0, i);
        r.addJarPath(head);
        String tail = path.substring(i + 2, path.length() - 6);
        return r.getDecompilationFor(tail);
    }

    enum Protocol {

        FILE(Impl::fuck),
        JAR(Impl::clazz);

        private final Impl impl;

        Protocol(Impl impl) {
            this.impl = impl;
        }
    }

    static String decompile(String protocol, String path) {
        try {
            return Protocol.valueOf(protocol.toUpperCase()).impl.decompile(path);
        } catch (Exception ignore) {
        }
        return "Plz feed back! (" + protocol + "|" + path + ")";
    }

}
