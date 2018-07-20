/**
 * Materia.js
 *
 * @description :: A model definition.  Represents a database table/collection/etc.
 * @docs        :: https://sailsjs.com/docs/concepts/models-and-orm/models
 */

module.exports = {

  attributes: {
    nombre:{
      type:"number"
    },
    codigo:{
      type:"string"
    },
    descripcion:{
      type:"string"
    },
   activo:{
      type:"string"
    },
    fechaCreacion:{
      type:"string"
    },
    numeroHorasPorSemana:{
      type:"number"
    },

    estudianteId:{
      type:"number"
    }
  },

};

