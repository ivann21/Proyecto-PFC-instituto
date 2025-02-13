-- PUBLIC.INSTITUTO.AULAS definition

-- Drop table

-- DROP TABLE PUBLIC.INSTITUTO.AULAS;

CREATE TABLE PUBLIC.INSTITUTO.AULAS (
	"ID aula" INTEGER NOT NULL IDENTITY,
	NOMBRE VARCHAR(100),
	UBICACION VARCHAR(100),
	CAPACIDAD_AULA INTEGER,
	PIZARRA BOOLEAN,
	ORDENADORES BOOLEAN,
	CONSTRAINT AULAS_PK PRIMARY KEY ("ID aula")
);


-- PUBLIC.INSTITUTO.CICLOS definition

-- Drop table

-- DROP TABLE PUBLIC.INSTITUTO.CICLOS;

CREATE TABLE PUBLIC.INSTITUTO.CICLOS (
	"ID Ciclos" INTEGER NOT NULL IDENTITY,
	NOMBRE VARCHAR(100),
	ANIO INTEGER,
	DESCRIPCION VARCHAR(255),
	CONSTRAINT CICLOS_PK PRIMARY KEY ("ID Ciclos")
);


-- PUBLIC.INSTITUTO.PROFESORES definition

-- Drop table

-- DROP TABLE PUBLIC.INSTITUTO.PROFESORES;

CREATE TABLE PUBLIC.INSTITUTO.PROFESORES (
	"ID profesor" INTEGER NOT NULL IDENTITY,
	NOMBRE VARCHAR(100),
	APELLIDO VARCHAR(100),
	CORREOELECTRONICO VARCHAR(100),
	CONTRASENIA VARCHAR(100),
	CONSTRAINT PROFESORES_PK PRIMARY KEY ("ID profesor")
);


-- PUBLIC.INSTITUTO.ROLES definition

-- Drop table

-- DROP TABLE PUBLIC.INSTITUTO.ROLES;

CREATE TABLE PUBLIC.INSTITUTO.ROLES (
	ID_ROL INTEGER NOT NULL IDENTITY,
	NOMBRE_ROL VARCHAR(100),
	DESCRIPCION VARCHAR(255),
	CONSTRAINT ROLES_PK PRIMARY KEY (ID_ROL)
);


-- PUBLIC.INSTITUTO.ALUMNOS definition

-- Drop table

-- DROP TABLE PUBLIC.INSTITUTO.ALUMNOS;

CREATE TABLE PUBLIC.INSTITUTO.ALUMNOS (
	"ID alumno" INTEGER NOT NULL IDENTITY,
	NOMBRE VARCHAR(100),
	APELLIDO VARCHAR(100),
	CORREOELECTRONICO VARCHAR(100),
	"ID ciclos" INTEGER,
	CONSTRAINT ALUMNOS_PK PRIMARY KEY ("ID alumno"),
	CONSTRAINT ALUMNOS_CICLOS_FK FOREIGN KEY ("ID ciclos") REFERENCES PUBLIC.INSTITUTO.CICLOS("ID Ciclos")
);
CREATE INDEX ALUMNOS_CICLOS_FK ON PUBLIC.INSTITUTO.ALUMNOS ("ID ciclos");


-- PUBLIC.INSTITUTO.ASIGNACION_ROLES definition

-- Drop table

-- DROP TABLE PUBLIC.INSTITUTO.ASIGNACION_ROLES;

CREATE TABLE PUBLIC.INSTITUTO.ASIGNACION_ROLES (
	ID_PROFESOR INTEGER NOT NULL,
	ID_ROL INTEGER NOT NULL,
	CONSTRAINT ASIGNACION_ROLES_PK PRIMARY KEY (ID_PROFESOR,ID_ROL),
	CONSTRAINT ASIGNACION_ROLES_PROFESOR_FK FOREIGN KEY (ID_PROFESOR) REFERENCES PUBLIC.INSTITUTO.PROFESORES("ID profesor") ON DELETE CASCADE,
	CONSTRAINT ASIGNACION_ROLES_ROL_FK FOREIGN KEY (ID_ROL) REFERENCES PUBLIC.INSTITUTO.ROLES(ID_ROL)
);
CREATE INDEX ASIGNACION_ROLES_PROFESOR_FK ON PUBLIC.INSTITUTO.ASIGNACION_ROLES (ID_PROFESOR);
CREATE INDEX ASIGNACION_ROLES_ROL_FK ON PUBLIC.INSTITUTO.ASIGNACION_ROLES (ID_ROL);


-- PUBLIC.INSTITUTO.ASIGNATURAS definition

-- Drop table

-- DROP TABLE PUBLIC.INSTITUTO.ASIGNATURAS;

CREATE TABLE PUBLIC.INSTITUTO.ASIGNATURAS (
	"ID Asignatura" INTEGER NOT NULL IDENTITY,
	NOMBRE VARCHAR(100),
	DESCRIPCION VARCHAR(200),
	ID_CICLOS INTEGER,
	CONSTRAINT ASIGNATURAS_PK PRIMARY KEY ("ID Asignatura"),
	CONSTRAINT ASIGNATURAS_CICLOS_FK FOREIGN KEY (ID_CICLOS) REFERENCES PUBLIC.INSTITUTO.CICLOS("ID Ciclos")
);
CREATE INDEX ASIGNATURAS_CICLOS_FK ON PUBLIC.INSTITUTO.ASIGNATURAS (ID_CICLOS);


-- PUBLIC.INSTITUTO.HORARIOS definition

-- Drop table

-- DROP TABLE PUBLIC.INSTITUTO.HORARIOS;

CREATE TABLE PUBLIC.INSTITUTO.HORARIOS (
	"ID horario" INTEGER NOT NULL IDENTITY,
	"ID asignatura" INTEGER,
	"ID profesor" INTEGER,
	"ID aula" INTEGER,
	"dia de la semana" VARCHAR(100),
	"hora de inicio" TIME,
	"hora de fin" TIME,
	CONSTRAINT HORARIOS_PK PRIMARY KEY ("ID horario"),
	CONSTRAINT HORARIOS_ASIGNATURAS_FK FOREIGN KEY ("ID asignatura") REFERENCES PUBLIC.INSTITUTO.ASIGNATURAS("ID Asignatura"),
	CONSTRAINT HORARIOS_AULAS_FK FOREIGN KEY ("ID aula") REFERENCES PUBLIC.INSTITUTO.AULAS("ID aula"),
	CONSTRAINT HORARIOS_PROFESORES_FK FOREIGN KEY ("ID profesor") REFERENCES PUBLIC.INSTITUTO.PROFESORES("ID profesor")
);
CREATE INDEX HORARIOS_ASIGNATURAS_FK ON PUBLIC.INSTITUTO.HORARIOS ("ID asignatura");
CREATE INDEX HORARIOS_AULAS_FK ON PUBLIC.INSTITUTO.HORARIOS ("ID aula");
CREATE INDEX HORARIOS_PROFESORES_FK ON PUBLIC.INSTITUTO.HORARIOS ("ID profesor");


-- PUBLIC.INSTITUTO.RESERVAS definition

-- Drop table

-- DROP TABLE PUBLIC.INSTITUTO.RESERVAS;

CREATE TABLE PUBLIC.INSTITUTO.RESERVAS (
	ID_RESERVA INTEGER NOT NULL IDENTITY,
	ID_AULA INTEGER,
	ID_PROFESOR INTEGER,
	FECHA_RESERVA DATE,
	HORA_RESERVA TIME,
	HORA_FIN_RESERVA TIME,
	CONSTRAINT RESERVAS_PK PRIMARY KEY (ID_RESERVA),
	CONSTRAINT RESERVAS_AULAS_FK FOREIGN KEY (ID_AULA) REFERENCES PUBLIC.INSTITUTO.AULAS("ID aula"),
	CONSTRAINT RESERVAS_PROFESORES_FK FOREIGN KEY (ID_PROFESOR) REFERENCES PUBLIC.INSTITUTO.PROFESORES("ID profesor")
);
CREATE INDEX RESERVAS_AULAS_FK ON PUBLIC.INSTITUTO.RESERVAS (ID_AULA);
CREATE INDEX RESERVAS_PROFESORES_FK ON PUBLIC.INSTITUTO.RESERVAS (ID_PROFESOR);