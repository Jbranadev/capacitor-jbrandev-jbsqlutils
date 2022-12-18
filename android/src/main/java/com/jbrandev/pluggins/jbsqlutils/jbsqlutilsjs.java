package com.jbrandev.pluggins.jbsqlutils;

import static io.github.josecarlosbran.JBSqlUtils.Utilities.UtilitiesJB.stringIsNullOrEmpty;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.josebran.LogsJB.LogsJB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.github.josecarlosbran.JBSqlUtils.Column;
import io.github.josecarlosbran.JBSqlUtils.DataBase.CreateTable;
import io.github.josecarlosbran.JBSqlUtils.DataBase.Delete;
import io.github.josecarlosbran.JBSqlUtils.DataBase.InsertInto;
import io.github.josecarlosbran.JBSqlUtils.DataBase.Select;
import io.github.josecarlosbran.JBSqlUtils.DataBase.Set;
import io.github.josecarlosbran.JBSqlUtils.DataBase.Update;
import io.github.josecarlosbran.JBSqlUtils.DataBase.Value;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.Constraint;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.DataBase;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.DataType;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.Operator;
import io.github.josecarlosbran.JBSqlUtils.Enumerations.OrderType;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.ValorUndefined;
import io.github.josecarlosbran.JBSqlUtils.JBSqlUtils;

public class jbsqlutilsjs {
    //Definicion de metodos
    Context contexto;

    jbsqlutilsjs(Context context){
        this.contexto=context;
    }


    /**
     * Setea las propiedades de conexión a BD's
     * @param propertysConection JsonObject con las propiedades de conexción
     * @throws DataBaseUndefind Lanza esta excepción si no esta definido el tipo de BD's a conectarse
     * @throws PropertiesDBUndefined Lanza esta excepción si no esta definido el nombre de la BD's a conectarse,
     * en el caso de BD's diferentes a SQLite, si falta el puerto, host, user, password, lanzara esta excepción
     */
    public void setearPropiedadesConexión(JSObject propertysConection) throws DataBaseUndefind, PropertiesDBUndefined {
        String databasetypestring=propertysConection.getString("dataBaseType", null);
        String dataBase=propertysConection.getString("dataBase", null);
        String port=propertysConection.getString("port", null);
        String host=propertysConection.getString("host", null);
        String user=propertysConection.getString("user", null);
        String password=propertysConection.getString("password", null);
        if(!stringIsNullOrEmpty(databasetypestring)){
            JBSqlUtils.setDataBaseTypeGlobal(DataBase.SQLite.getNumeracionforName(databasetypestring));
            if(DataBase.SQLite.getNumeracionforName(databasetypestring)==DataBase.SQLite){
                String separador = System.getProperty("file.separator");
                dataBase.replace("/", separador);
                String rutaBase= null;
                File directorioBase=contexto.getCacheDir();
                if(!directorioBase.exists()){
                    directorioBase.mkdir();
                }
                File directorioDB=new File(directorioBase, dataBase);
                rutaBase = directorioBase.getPath();
                LogsJB.info("Ruta base de BD's SQLite: "+rutaBase);
                LogsJB.info("Ruta de BD's SQLite: "+directorioDB.getPath());
                dataBase=directorioDB.getPath();
            }
            if(!stringIsNullOrEmpty(dataBase)){
                JBSqlUtils.setDataBaseGlobal(dataBase);
                if(!stringIsNullOrEmpty(port)){
                    JBSqlUtils.setPortGlobal(port);
                }else{
                    if(DataBase.SQLite.getNumeracionforName(databasetypestring)!=DataBase.SQLite){
                        throw new PropertiesDBUndefined("No se a seteado el puerto en el que se encuentra escuchando la BD's a la cual deseamos se pegue JBSqlUtils");
                    }
                }

                if(!stringIsNullOrEmpty(host)){
                    JBSqlUtils.setHostGlobal(host);
                }else{
                    if(DataBase.SQLite.getNumeracionforName(databasetypestring)!=DataBase.SQLite){
                        throw new PropertiesDBUndefined("No se a seteado el host en el que se encuentra la BD's a la cual deseamos se pegue JBSqlUtils");
                    }
                }

                if(!stringIsNullOrEmpty(user)){
                    JBSqlUtils.setUserGlobal(user);
                }else{
                    if(DataBase.SQLite.getNumeracionforName(databasetypestring)!=DataBase.SQLite){
                        throw new PropertiesDBUndefined("No se a seteado el usuario de la BD's a la cual deseamos se pegue JBSqlUtils");
                    }
                }

                if(!stringIsNullOrEmpty(password)){
                    JBSqlUtils.setPasswordGlobal(password);
                }else{
                    if(DataBase.SQLite.getNumeracionforName(databasetypestring)!=DataBase.SQLite){
                        throw new PropertiesDBUndefined("No se a seteado la contraseña del usuario de la BD's a la cual deseamos se pegue JBSqlUtils");
                    }
                }
            }else{
                throw new PropertiesDBUndefined("No se a seteado la BD's a la cual deseamos se pegue JBSqlUtils");
            }
        }else{
            throw new DataBaseUndefind("No se a seteado la DataBase que índica a que BD's deseamos se pegue JBSqlUtils");
        }
    }

    public boolean dropTableIfExist(String TableName) throws DataBaseUndefind, PropertiesDBUndefined, ValorUndefined {
        return JBSqlUtils.dropTableIfExist(TableName).execute();
    }

    public List<Column> getColumns(JSArray columnsarray) throws JSONException {
        List<Column> columnas=new ArrayList<>();
        List<JSObject> colums=columnsarray.toList();
        for(int j=0; j<colums.size();j++){
            JSONObject column=colums.get(j);
            String name=column.optString("name");
            String default_value=column.optString("default_value");
            String tempType=column.optString("dataTypeSQL");
            DataType tipo= DataType.CHAR.getNumeracionforName(tempType);
            //Obtenemos las restricciones
            JSONArray restriccionesJson=column.optJSONArray("restriccions");
            List<String> restriccions=new ArrayList<>();
            List<Constraint> restriccionesList=new ArrayList<>();
            if(!Objects.isNull(restriccionesJson)){
                for(int i=0; i<restriccionesJson.length(); i++){
                    restriccions.add(restriccionesJson.getString(i));
                }

                for(String restricciontemp:restriccions){
                    restriccionesList.add(Constraint.AUTO_INCREMENT.getNumeracionforName(restricciontemp));
                }
            }
            Constraint[] restricciones=restriccionesList.toArray(new Constraint[0]);
            LogsJB.debug("Cantidad de restricciones para la columna: "+restricciones.length);
            if(restricciones.length==0){
                restricciones=null;
            }
            /**
             * Asignación de las columnas
             */
            if(Objects.isNull(restricciones) && stringIsNullOrEmpty(default_value)){
                columnas.add(new Column(name, tipo));
            }else if(Objects.isNull(restricciones)){
                columnas.add(new Column(name, tipo, default_value));
            }else{
                columnas.add(new Column(name, tipo, default_value, restricciones));
            }
        }
        return columnas;
    }

    public Boolean createTable(String tableName, List<Column> columnas) throws ValorUndefined, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Boolean respuesta=false;
        CreateTable create=JBSqlUtils.createTable(tableName);
        respuesta=createTable(create, columnas);
        return respuesta;
    }

    public  Boolean createTable(Object invocador,  List<Column> columnas) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if(!columnas.isEmpty()){
            Column temp=columnas.remove(0);
            Method metodo=getMethodforName(getMethodsModel(invocador),"addColumn");
            return createTable(metodo.invoke(invocador, temp), columnas);
        }else{
            Method metodo=getMethodforName(getMethodsModel(invocador),"createTable");
            return (Boolean) metodo.invoke(invocador, null);
        }
    }

    public int insertInto(String tableName, JSArray valuesarray) throws JSONException, ValorUndefined, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        List<JSObject> values=valuesarray.toList();
        JSONObject temp=values.remove(0);
        Value ejecutora= JBSqlUtils.insertInto(tableName).value(temp.getString("columName"), temp.get("value"));
        return insertInto(ejecutora, values);
    }

    public  int insertInto(Object invocador,  List<JSObject> columnas) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, JSONException {
        if(!columnas.isEmpty()){
            JSONObject temp=columnas.remove(0);
            Method metodo=getMethodforName(getMethodsModel(invocador),"andValue");
            return insertInto(metodo.invoke(invocador, temp.getString("columName"), temp.get("value")), columnas);
        }else{
            Method metodo=getMethodforName(getMethodsModel(invocador),"execute");
            return (int) metodo.invoke(invocador, null);
        }
    }

    public int update(String tableName, JSObject valueUpdate) throws ValorUndefined, JSONException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Set invocador= JBSqlUtils.update(tableName).set(valueUpdate.getString("columName"), valueUpdate.get("value"));
        return update(invocador, valueUpdate);
    }


    public int update(Object invocador, JSObject valueUpdate) throws JSONException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        JSObject andValueUpdate=valueUpdate.getJSObject("andValueUpdate", null);
        JSObject where=valueUpdate.getJSObject("where", null);
        if(!Objects.isNull(where)){
            Method metodo=getMethodforName(getMethodsModel(invocador),"where");
            return where(metodo.invoke(invocador, where.getString("columName"), Operator.AND.getNumeracionforName(where.getString("operator")), where.get("value")), where);
        }else if(!Objects.isNull(andValueUpdate)){
            Method metodo=getMethodforName(getMethodsModel(invocador),"andSet");
            return update(metodo.invoke(invocador, andValueUpdate.getString("columName"), andValueUpdate.get("value") ), andValueUpdate);
        }else{
            Method metodo=getMethodforName(getMethodsModel(invocador),"execute");
            return (int) metodo.invoke(invocador, null);
        }
    }

    public int delete(String tableName, JSObject where) throws ValorUndefined, NoSuchMethodException, JSONException, InvocationTargetException, IllegalAccessException {
        Delete delete = JBSqlUtils.delete(tableName);
        if(Objects.isNull(where)){
            return 0;
        }
        Method metodo=getMethodforName(getMethodsModel(delete),"where");
        return where(metodo.invoke(delete, where.getString("columName"), Operator.AND.getNumeracionforName(where.getString("operator")), where.get("value")), where);

    }


    public int where(Object invocador, JSObject where) throws JSONException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        JSObject and=where.getJSObject("and", null);
        JSObject or=where.getJSObject("or", null);
        JSObject orderBy=where.getJSObject("orderBy", null);
        JSObject take=where.getJSObject("take", null);
        if(!Objects.isNull(and)){
            Method metodo=getMethodforName(getMethodsModel(invocador),"and");
            return where(metodo.invoke(invocador, and.getString("columName"), Operator.IGUAL_QUE.getNumeracionforName(and.getString("operator")), and.get("value")), and);
        }else if(!Objects.isNull(or)){
            Method metodo=getMethodforName(getMethodsModel(invocador),"or");
            return where(metodo.invoke(invocador, or.getString("columName"), Operator.IGUAL_QUE.getNumeracionforName(or.getString("operator")), or.get("value")), or);
        }else if(!Objects.isNull(orderBy)){
            Method metodo=getMethodforName(getMethodsModel(invocador),"orderBy");
            return where(metodo.invoke(invocador, orderBy.getString("columName"), OrderType.ASC.getNumeracionforName(orderBy.getString("orderType"))), orderBy);
        }else if(!Objects.isNull(take)){
            Method metodo=getMethodforName(getMethodsModel(invocador),"take");
            return where(metodo.invoke(invocador, take.getInteger("limite", 1)), take);
        }else{
            Method metodo=getMethodforName(getMethodsModel(invocador),"execute");
            return (int) metodo.invoke(invocador, null);
        }
    }


    public List<JSONObject> select(String tableName, JSObject where, JSArray columnasJson) throws JSONException, DataBaseUndefind, PropertiesDBUndefined, ValorUndefined, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        List<String> columnas=null;
        List<String> columnastemp=new ArrayList<>();
        if(!Objects.isNull(columnasJson)){
            columnas=new ArrayList<>();
            columnas=columnasJson.toList();
        }
        Select select=JBSqlUtils.select(tableName);
        if(!Objects.isNull(where)){
            Method metodo=getMethodforName(getMethodsModel(select),"where");
            return where(metodo.invoke(select, where.getString("columName"), Operator.AND.getNumeracionforName(where.getString("operator")), where.get("value")), where, columnas);
        }else{
            Method metodo=getMethodforName(getMethodsModel(select),"getInJsonObjects");
            return (List<JSONObject>) metodo.invoke(select, columnas);
        }
    }

    public List<JSONObject> where(Object invocador, JSObject where, List<String> columnas) throws JSONException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<String> columnastemp=new ArrayList<>();
        JSObject and=where.getJSObject("and", null);
        JSObject or=where.getJSObject("or", null);
        JSObject orderBy=where.getJSObject("orderBy", null);
        JSObject take=where.getJSObject("take", null);
        if(!Objects.isNull(and)){
            Method metodo=getMethodforName(getMethodsModel(invocador),"and");
            return where(metodo.invoke(invocador, and.getString("columName"), Operator.IGUAL_QUE.getNumeracionforName(and.getString("operator")), and.get("value")), and, columnas);
        }else if(!Objects.isNull(or)){
            Method metodo=getMethodforName(getMethodsModel(invocador),"or");
            return where(metodo.invoke(invocador, or.getString("columName"), Operator.IGUAL_QUE.getNumeracionforName(or.getString("operator")), or.get("value")), or, columnas);
        }else if(!Objects.isNull(orderBy)){
            Method metodo=getMethodforName(getMethodsModel(invocador),"orderBy");
            return where(metodo.invoke(invocador, orderBy.getString("columName"), OrderType.ASC.getNumeracionforName(orderBy.getString("orderType"))), orderBy, columnas);
        }else if(!Objects.isNull(take)){
            Method metodo=getMethodforName(getMethodsModel(invocador),"take");
            return where(metodo.invoke(invocador, take.getInteger("limite", 1)), take, columnas);
        }else{
            Method metodo=getMethodforName(getMethodsModel(invocador),"getInJsonObjects");
            return (List<JSONObject>) metodo.invoke(invocador, columnas);
        }
    }


    public List<Method> getMethodsModel(Object invocador) {
        Method[] metodos = invocador.getClass().getMethods();
        List<Method> result = new ArrayList<>();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            // Los muestro en consola
            for (Method metodo : metodos) {
                String clase = metodo.getDeclaringClass().getSimpleName();
                String returntype = metodo.getReturnType().getSimpleName();
                Parameter[] parametros = new Parameter[0];

                parametros = metodo.getParameters();

                String ParametroType = "";
                if (parametros.length >= 1) {
                    ParametroType = parametros[0].getType().getSimpleName();
                }

                if ((clase.equalsIgnoreCase("Object")
                        || clase.equalsIgnoreCase("Methods_Conexion"))
                    //&& !(returntype.equalsIgnoreCase("Column") ||ParametroType.equalsIgnoreCase("Column"))
                ) {

                } else {
                    //System.out.println(metodo.getName() + "   " + metodo.getDeclaringClass() + "  " + returntype+"  " + ParametroType);
                    result.add(metodo);
                }
                //System.out.println(metodo.getName()+"   "+metodo.getDeclaringClass()+"  "+returntype);
            }
        }
        return result;
    }


    public Method getMethodforName(List<Method> metodos, String nombre){
        Method result=null;
        for(Method metodo:metodos){
            if(metodo.getName().equalsIgnoreCase(nombre)){
                return metodo;
            }
        }

        return result;
    }








}
