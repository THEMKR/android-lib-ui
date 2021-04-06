package com.mkr.lib.sqlite

/**
 * @author THEMKR
 * Class to hold the column Info
 */
class Column {
    val name: String
    val type: ColumnType
    val propertyList: Array<ColumnProperty>
    val foreignReference: ForeignReference?

    /**
     * Constructor
     * @param name
     * @param type
     * @param propertyArray
     * @param foreignReference
     */
    private constructor(name: String, type: ColumnType, propertyList: Array<ColumnProperty>, foreignReference: ForeignReference?) {
        this.name = name
        this.type = type
        this.propertyList = propertyList
        this.foreignReference = foreignReference
    }

    override fun toString(): String {
        return super.toString() + " : $name    $type    $propertyList    $foreignReference"
    }

    /**
     * Builder to build the Column of a table
     */
    class Builder {
        private val name: String
        private var type: ColumnType = ColumnType.STRING
        private val property: ArrayList<ColumnProperty> = ArrayList()
        private var foreignReference: ForeignReference? = null

        /**
         * Constructor
         * @param name
         */
        constructor(name: String) {
            this.name = name
        }

        /**
         * Method to set the Type of the Column
         * @param type
         */
        fun setType(type: ColumnType): Builder {
            this.type = type
            return this
        }

        /**
         * Method to set the Foreign Key Reference
         * @param foreignReference
         */
        fun setForeignReference(foreignReference: ForeignReference): Builder {
            addProperty(ColumnProperty.FOREIGN_KEY)
            this.foreignReference = foreignReference
            return this
        }

        /**
         * Method to add the Property of the Column
         * @param property
         */
        fun addProperty(property: ColumnProperty): Builder {
            if (!this.property.contains(property)) {
                this.property.add(property)
            }
            return this
        }

        /**
         * Method to build the column of table
         * @return Table Column
         */
        fun build(): Column {
            return Column(name, type, property.toTypedArray(), foreignReference)
        }
    }
}