/**
 * Orden.js
 *
 * @description :: A model definition.  Represents a database table/collection/etc.
 * @docs        :: https://sailsjs.com/docs/concepts/models-and-orm/models
 */

module.exports = {

  attributes: {

   cedulaIdentidad: {
     type: "number"
   },
    sector: {
     type: "string"
    },
    idMateria: {
     type: "number"
    }

  },

};

