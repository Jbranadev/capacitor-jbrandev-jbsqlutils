import type { PermissionState } from "@capacitor/core";

export interface jbsqlutilsjsPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;

  /**
   * Elimina la tabla especificada como parametro
   * @param {DropTableOptions} droptableOptions Nombre de la tabla a eliminar
   * @returns {DropTableResult}  True si la tabla fue eliminada de BD's, False si la tabla no existe en BD's o si
   * sucedio algun error al momento de eliminar la tabla
   */
  dropTableIfExist( droptableOptions:DropTableOptions):Promise<DropTableResult>;

  /**
   * Crea la tabla especificada como parametro
   * @param {CreateTableOptions} createtableOptions Definicion de la tabla a crear
   * @returns {CreateTableResult} True si la tabla fue creada en BD's, False si la tabla ya existe en BD's
   * o si sucedio algun error al momento de crear la tabla
   */
  createTable(createtableOptions:CreateTableOptions):Promise<CreateTableResult>;

  /**
   * Inserta un nuevo registro en la tabla proporcionada con los valores indicados en insertOptions
   * @param {InsertOptions} insertOptions Tabla sobre la cual se realizara el Insert y los Valores a insertar
   * @returns {InsertResult} Cantidad de filas Insertadas
   */
  insertInto(insertOptions:InsertOptions):Promise<InsertResult>;



}








/**
 * Define la tabla sobre la cual se realizara el Insert y los valores a insertar
 */
export interface InsertOptions{
  /**
   * Nombre de la tabla sobre la que se efectuara el Insert
   * @type{string}
   */
  tableName:string;
  /**
   * Array de valores a insertar en la tabla.
   * @type {valuesInsert[]}
   */
  values:valuesInsert[];
}


/**
 * Define el valor a insertar y en que columna
 */
export interface valuesInsert{
  /**
   * Nombre de la columna en la que se insertara el valor proporcionado
   * @type{string}
   */
  columName:string;
  /**
   * Valor a insertar
   * @type{any}
   */
  value:any;
}


/**
 * Define el resultado de insertar un registro en BD's
 */
export interface InsertResult{
  /**
   * Cantidad de filas que se han visto afectadas al ejecutar la sentencia SQL.
   * @type {number}
   */
  rows_insert:number;
}


/**
 * Representación de la tabla a crear en BD's a traves del metodo createTable
 */

export interface CreateTableOptions{
  /**
   * Nombre de la tabla a crear
   * @type {string}
   */
  tableName:string;
  /**
   * Array de Columnas que tendra la tabla al momento de ser creada en BD's
   * @type{Column[]}
   */
  columnas:Column[];
}


/**
 * Representación de una columna a crear en BD's
 */
export interface Column{
  /**
   * Nombre de la columna
   * @type {string}
   */
  name:string;
  /**
   * Indica el valor por default que tendra la columna en BD's
   * Es importante que para que este sea efectivo, agregar en las restriccions, la Constraint.DEFAULT.
   * @type {string}
   */
  default_value?:string;
  /**
   * DataType que indica el tipo de dato SQL que almacenara la columna.
   * @type {DataType}
   */
  dataTypeSQL:DataType;
  /**
   * Array que Indica las restricciones SQL que tendra este campo.
   * @type {Constraint[]}
   */
  restriccions?:Constraint[];
}

/**
 * Resultado del metodo createTable
 */
 export interface CreateTableResult{
  /**
   * True si la tabla a sido creada, false si la tabla ya existe en BD's o si sucede un error 
   * al momento de ejecutar la sentencia SQL
   * @type {boolean}
   */
  execute:boolean;
}




/**
 * Opciones disponibles a envíar en el metodo DropTableIfExist
 */
export interface DropTableOptions{
  /**
   * Nombre de la tabla a eliminar
   * @type {string}
   */
  tableName:string;
}

/**
 * Resultado del metodo DropTableIfExist
 */
export interface DropTableResult{
  /**
   * True si la tabla fue eliminada en BD's, False si la tabla no existe en BD's o si 
   * sucede algun problema al ejecutar la sentencia SQL.
   * @type {boolean}
   */
  execute:boolean;
}




/**
 * Tipos de restricciones que puede tener una columna al momento de solicitar su creación a
 * JBSqlUtils
 * @enum {string}
 */
export enum Constraint {

  /**
     * Indica que la columna no acepta valores Nullos.
     */
  NOT_NULL = "NOT_NULL",

  /**
   * El valor de esta columna tiene que ser unico
   */
  UNIQUE = "UNIQUE",

  /**
   * Restriccion que permite indicarle que tipo de valores si serán aceptados por la columna
   * Lo puede realizar a travez del metodo setRestriccion=String restriccion; de esta numeración.
   * considerar que la misma restricción se aplicara para el resto de columnas que tengan un valor Check.
   */
  CHECK = "CHECK",

  /**
   * Indica que la columna funciona como clave primaria del modelo.
   */
  PRIMARY_KEY = "PRIMARY_KEY",


  /**
   * Indica que la columna funciona como clave foranea del modelo.
   */
  FOREIGN_KEY = "FOREIGN_KEY",

  /**
   * Indica que el campo tendra como valor por default el TimeStamp del momento en que se almacene el modelo.
   */
  CURRENT_TIMESTAMP = "CURRENT_TIMESTAMP",


  /**
   * Indica que la columna tendra un valor por default
   */
  DEFAULT = "DEFAULT",

  /**
   * Indica que la columna autoincrementara su valor cada vez que se almacene un registro en la tabla correspondiente al modelo.
   */
  AUTO_INCREMENT = "AUTO_INCREMENT",



}

/**
 * Numeración de los tipos de Bases de Datos a los que se puede conectar JBSqlUtils
 * @enum {string}
 */
export enum DataBase {

  /**
     * SQLite
     */
  SQLite = "SQLite",

  /**
   * MySQL
   */
  MySQL = "MySQL",

  /**
   * SQL Server
   */
  SQLServer = "SQLServer",

  /**
   * PostgreSQL
   */
  PostgreSQL = "PostgreSQL",
}

/**
 * Numeración de los tipos de datos soportados por JBSqlUtils
 * @enum {string}
 */
export enum DataType {
  /**
     * Entero de 32 bits con signo
     */
  INTEGER = "INTEGER",

  /**
   * Entero de 32 bits con signo
   */
  INT = "INT",

  /**
   * Tipo de dato auto incrementable en SQL Server
   */
  IDENTITY = "IDENTITY",

  /**
   * Tipo de dato auto incrementable en PostgreSQL
   */
  SERIAL = "SERIAL",
  //java.lang.Integer



  /**
   * Cadena de caracteres de longitud fija
   */
  CHAR = "CHAR",

  /**
   * Cadena de caracteres de longitud variable
   */
  VARCHAR = "VARCHAR",

  /**
   * Cadenas de cualquier longitud (varios megabytes), debe definir el tamaño que desea tenga la columna
   * por medio del metodo setSize, para esta numeración en especifico.
   */
  LONGVARCHAR = "LONGVARCHAR",

  /**
   * Enum se tienen que definir las opciones disponibles a través del metodo setSize, envíando como parametro
   * para esta numeración, las opciones que deseamos tenga disponible
   */
  ENUM = "ENUM",



  /**
   * entero de 16 bits
   */
  SMALLINT = "SMALLINT",

  /**
   * entero de 16 bits
   */
  TINYINT = "TINYINT",



  /**
   * Valor de coma flotante
   */
  REAL = "REAL",

  /**
   * Valor de coma flotante
   */
  FLOAT = "FLOAT",



  /**
   * Gran valor de punto flotante
   */
  DOUBLE = "DOUBLE",


  /**
   * Valores decimales de precisión absoluta
   */
  NUMERIC = "NUMERIC",

  /**
   * Valor decimal de precisión absoluta
   */
  DECIMAL = "DECIMAL",

  /**
   * Valor decimal de precisión absoluta
   */
  MONEY = "MONEY",

  /**
   * Valor decimal de precisión absoluta
   */
  SMALLMONEY = "SMALLMONEY",




  /**
   * Bit único/valor binario (activado o desactivado)
   */
  BIT = "BIT",

  /**
   * Valor Booleano
   */
  BOOLEAN = "BOOLEAN",

  /**
   * Valor Booleano
   */
  BOOL = "BOOL",




  /**
   * Matriz de valores binarios
   */
  BINARY = "BINARY",

  /**
   * Matriz de longitud variable de valores binarios, en mysql el valor maximo es de 21844
   * pero en sql server es de 8000
   */
  VARBINARY = "VARBINARY",

  /**
   * Matriz de valores binarios de cualquier longitud (varios megabytes)
   * SQL Server
   */
  LONGVARBINARY = "LONGVARBINARY",


  /**
   * Cadena binaria de ancho variable
   */
  IMAGE = "IMAGE",




  /**
   * Setea que el tipo de dato será un Object
   */
  OBJECT = "OBJECT",

  /**
   * Setea que el tipo de dato será un JSON
   */
  JSON = "JSON",




  /**
   * Valor de fecha
   */
  DATE = "DATE",



  /**
   * Valor del tiempo
   */
  TIME = "TIME",




  /**
   * Valor de tiempo con campo adicional de nanosegundos
   */
  TIMESTAMP = "TIMESTAMP",

  /**
   * Valor de tiempo con campo adicional de nanosegundos
   */
  SMALLDATETIME = "SMALLDATETIME",

  /**
   * Valor de tiempo con campo adicional de nanosegundos
   */
  DATETIME = "DATETIME",


  /**
   * Valor de tiempo con campo adicional de nanosegundos
   */
  DATETIME2 = "DATETIME2",

}

/**
 * Numeración de los operadores disponibles para filtrar las consultas SQL
 * @enum {string}
 */
export enum Operator {

  /**
     * Operador >=
     */
  MAYOR_IGUAL_QUE = "MAYOR_IGUAL_QUE",
  /**
   * Operador >
   */
  MAYOR_QUE = "MAYOR_QUE",
  /**
   * Operador =
   */
  IGUAL_QUE = "IGUAL_QUE",
  /**
   * Operador <>
   */
  DISTINTO = "DISTINTO",
  /**
   * Operador <
   */
  MENOR_QUE = "MENOR_QUE",
  /**
   * Operador <=
   */
  MENOR_IGUAL_QUE = "MENOR_IGUAL_QUE",

  /**
   * Operador LIKE
   */
  LIKE = "LIKE",
  /**
   * Operador (
   */
  OPEN_PARENTESIS = "OPEN_PARENTESIS",
  /**
   * Operador )
   */
  CLOSE_PARENTESIS = "CLOSE_PARENTESIS",
  /**
   * Operador ORDER BY
   */
  ORDERBY = "ORDERBY",

  /**
   * Operador AND
   */
  AND = "AND",

  /**
   * Operador OR
   */
  OR = "OR",

  /**
   * Operador NOT
   */
  NOT = "NOT",
}

/**
 * Numeración que permite indicar que tipo de ordenamiento se desea realizar a los 
 * resultados de la consulta a BD's
 * @enum {string}
 */
export enum OrderType {
  /**
     * Indica que deseamos se ordene de manera Ascendente los registros
     */
  ASC = "ASC",

  /**
   * Indica que deseamos se ordene de manera Descendente los registros
   */
  DESC = "DESC",

}



/**
 * @interface PermissionStatus
 * Sirve para que el pluggin pueda identificar si la aplicación cuenta con los permisos requeridos
 * para el funcionamiento correcto de este.
 */
 export interface PermissionStatus {
  // TODO: change 'location' to the actual name of your alias!
  location: PermissionState;
}

