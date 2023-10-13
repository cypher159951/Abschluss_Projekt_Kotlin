package com.example.paymessage.data.datamodels


/**
 * Eine Datenklasse, die ein Vorschaubild repräsentiert.
 * @param alttext Der alternative Text des Bildes als String.
 * @param imageVariants Eine Instanz der Datenklasse ImageVariant, die die verschiedenen Varianten des Bildes enthält.
 */
data class TeaserImage(
    val alttext: String,
    val imageVariants: ImageVariant
)
