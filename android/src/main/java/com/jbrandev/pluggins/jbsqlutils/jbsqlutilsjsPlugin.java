package com.jbrandev.pluggins.jbsqlutils;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.josebran.LogsJB.LogsJB;
import com.josebran.LogsJB.Numeracion.NivelLog;

@CapacitorPlugin(name = "jbsqlutilsjs")
public class jbsqlutilsjsPlugin extends Plugin {

    private jbsqlutilsjs implementation = new jbsqlutilsjs();

    @Override
    public void load(){
        LogsJB.setGradeLog(NivelLog.INFO);
        LogsJB.setIsAndroid(true);
    }

    /*@PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");

        JSObject ret = new JSObject();
        ret.put("value", implementation.echo(value));
        call.resolve(ret);
    }*/

    @PluginMethod
    public void dropTableIfExist(PluginCall call){

    }
}
