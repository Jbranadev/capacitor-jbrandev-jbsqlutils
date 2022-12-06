package com.jbrandev.pluggins.jbsqlutils;

import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.josebran.LogsJB.LogsJB;
import com.josebran.LogsJB.Numeracion.NivelLog;

import io.github.josecarlosbran.JBSqlUtils.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.ValorUndefined;

@CapacitorPlugin(name = "jbsqlutilsjs")
public class jbsqlutilsjsPlugin extends Plugin {

    private jbsqlutilsjs implementation = new jbsqlutilsjs();

    @Override
    public void load(){
        LogsJB.setGradeLog(NivelLog.INFO);
        LogsJB.setIsAndroid(true);
    }

    @PluginMethod
    public void dropTableIfExist(PluginCall call){
        try{
            implementation.setearPropiedadesConexión(call.getObject("propertysConection"));
            Boolean resultado=implementation.dropTableIfExist(call.getString("tableName"));
            JSObject respuesta=new JSObject();
            respuesta.put("execute", resultado);
            call.resolve(respuesta);
        }catch (Exception e){
            LogsJB.fatal("Excepción disparada en el método dropTableIfExist: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
            call.reject(e.getLocalizedMessage(), null, e);
        }
    }

    @PluginMethod
    public void createTable(PluginCall call){
        try{
            implementation.setearPropiedadesConexión(call.getObject("propertysConection"));
            String tableName=call.getString("tableName");
            JSArray columnas=call.getArray("columnas");
            Boolean resultado=false;


            JSObject respuesta=new JSObject();
            respuesta.put("execute", resultado);
            call.resolve(respuesta);
        }catch (Exception e){
            LogsJB.fatal("Excepción disparada en el método createTable: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
            call.reject(e.getLocalizedMessage(), null, e);
        }
    }



}
