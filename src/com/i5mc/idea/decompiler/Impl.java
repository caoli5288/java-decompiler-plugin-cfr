package com.i5mc.idea.decompiler;

import org.benf.cfr.reader.PluginRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 17-3-8.
 */
public interface Impl {

    Map<String, String> OPT = new HashMap<String, String>() {{
        put("comments", "false");
        put("hideutf", "false");
    }};

    String decompile(String path);

    static String fuck(String path) {
        PluginRunner r = new PluginRunner(OPT);
        r.addJarPath(path);
        return r.getDecompilationFor(path);
    }

    static String clazz(String path) {
        int i = path.lastIndexOf("!/");// filesystem path can contain ! but java can not
        if (i == -1) throw new IllegalArgumentException();
        PluginRunner r = new PluginRunner(OPT);
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
