package com.jbrandev.pluggins.jbsqlutils;

import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.josebran.LogsJB.LogsJB;
import com.josebran.LogsJB.Numeracion.NivelLog;

import java.util.List;
import java.util.Objects;

import io.github.josecarlosbran.JBSqlUtils.Column;
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
            JSArray columns=call.getArray("columnas");
            Boolean resultado=false;
            List<Column> columnas=implementation.getColumns(columns);
            if(columnas.isEmpty()||columnas.size()==0){
                call.errorCallback("No especifico las columnas que debe tener la tabla.");
                call.reject("No especifico las columnas que debe tener la tabla.");
            }
            resultado=implementation.createTable(tableName, columnas);
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





    @PluginMethod
    public void insertInto(PluginCall call){
        try{
            implementation.setearPropiedadesConexión(call.getObject("propertysConection"));
            String tableName=call.getString("tableName");
            JSArray valuesInsert=call.getArray("values");
            int resultado=implementation.insertInto(tableName, valuesInsert);
            JSObject respuesta=new JSObject();
            respuesta.put("rows_insert", resultado);
            call.resolve(respuesta);
        }catch (Exception e){
            LogsJB.fatal("Excepción disparada en el método insertInto: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
            call.reject(e.getLocalizedMessage(), null, e);
        }
    }


    @PluginMethod
    public void update(PluginCall call){
        try{
            implementation.setearPropiedadesConexión(call.getObject("propertysConection"));
            String tableName=call.getString("tableName");
            JSObject valueUpdate=call.getObject("valueUpdate");
            int resultado=implementation.update(tableName, valueUpdate);
            JSObject respuesta=new JSObject();
            respuesta.put("rows_update", resultado);
            call.resolve(respuesta);
        }catch (Exception e){
            LogsJB.fatal("Excepción disparada en el método update: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
            call.reject(e.getLocalizedMessage(), null, e);
        }
    }


    @PluginMethod
    public void delete(PluginCall call){
        try{
            implementation.setearPropiedadesConexión(call.getObject("propertysConection"));
            String tableName=call.getString("tableName");
            JSObject where=call.getObject("where", null);
            if(Objects.isNull(where)){
                call.errorCallback("No especifico la sentencia where de filtro para eliminar filas de la tabla: "+tableName);
                call.reject("No especifico la sentencia where de filtro para eliminar filas de la tabla: "+tableName);
            }
            int resultado=implementation.delete(tableName, where);
            JSObject respuesta=new JSObject();
            respuesta.put("rows_delete", resultado);
            call.resolve(respuesta);
        }catch (Exception e){
            LogsJB.fatal("Excepción disparada en el método delete: " + e.toString());
            LogsJB.fatal("Tipo de Excepción : " + e.getClass());
            LogsJB.fatal("Causa de la Excepción : " + e.getCause());
            LogsJB.fatal("Mensaje de la Excepción : " + e.getMessage());
            LogsJB.fatal("Trace de la Excepción : " + e.getStackTrace());
            call.reject(e.getLocalizedMessage(), null, e);
        }
    }


}
