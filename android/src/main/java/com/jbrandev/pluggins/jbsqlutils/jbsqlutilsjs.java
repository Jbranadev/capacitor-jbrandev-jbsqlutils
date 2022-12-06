package com.jbrandev.pluggins.jbsqlutils;

import android.util.Log;

import io.github.josecarlosbran.JBSqlUtils.Exceptions.DataBaseUndefind;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.PropertiesDBUndefined;
import io.github.josecarlosbran.JBSqlUtils.Exceptions.ValorUndefined;
import io.github.josecarlosbran.JBSqlUtils.JBSqlUtils;

public class jbsqlutilsjs {
    //Definicion de metodos

    public void setearPropiedadesConexión(){
        
    }

    public boolean dropTableIfExist(String TableName) throws DataBaseUndefind, PropertiesDBUndefined, ValorUndefined {
        return JBSqlUtils.dropTableIfExist(TableName).execute();
    }

}
