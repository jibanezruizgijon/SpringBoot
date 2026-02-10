package com.example.demo.clases;

/**
 * Enumeración que define los movimientos artísticos o épocas históricas disponibles en la galería.
 * <p>
 * Se utiliza para categorizar los cuadros y facilitar el filtrado de obras en el buscador.
 * Los valores se almacenan en la base de datos como cadenas de texto (STRING) para mayor legibilidad.
 * @author Jonathan Ibáñez Piñero
 */
public enum EpocaPintura {
    /** Estilo artístico del siglo XVII y XVIII caracterizado por la ornamentación excesiva. */
    Barroco, 
    
    /** Movimiento del siglo XIX centrado en la luz y el instante. */
    Impresionismo, 
    
    /** Movimiento cultural y artístico de los siglos XV y XVI. */
    Renacimiento, 
    
    /** Estilo de vanguardia del siglo XX basado en formas geométricas. */
    Cubismo, 
    
    /** Extensión del impresionismo a finales del siglo XIX. */
    Postimpresionismo,
    
    /** Movimiento artístico abstracto, también conocido como De Stijl. */
    Neoplasticismo, 
    
    /** Estilo artístico de finales del Renacimiento. */
    Manierismo, 
    
    /** Movimiento de vanguardia que prioriza la expresión subjetiva. */
    Expresionismo, 
    
    /** Movimiento que busca trascender lo real a partir del impulso psíquico. */
    Surrealismo, 
    
    /** Movimiento cultural que prioriza los sentimientos. */
    Romanticismo, 
    
    /** Categoría general para obras provenientes de culturas orientales. */
    Arte_oriental, 
    
    /** Estilo artístico alegre y decorativo del siglo XVIII. */
    Rococo, 
    
    /** Movimiento pictórico francés caracterizado por el uso provocativo del color. */
    Fauvismo
}