
package com.health.client;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for calculateBodyFat complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="calculateBodyFat"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="waist" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="neck" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="height" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="hip" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="gender" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "calculateBodyFat", propOrder = {
    "waist",
    "neck",
    "height",
    "hip",
    "gender"
})
public class CalculateBodyFat {

    protected double waist;
    protected double neck;
    protected double height;
    protected double hip;
    protected String gender;

    /**
     * Gets the value of the waist property.
     * 
     */
    public double getWaist() {
        return waist;
    }

    /**
     * Sets the value of the waist property.
     * 
     */
    public void setWaist(double value) {
        this.waist = value;
    }

    /**
     * Gets the value of the neck property.
     * 
     */
    public double getNeck() {
        return neck;
    }

    /**
     * Sets the value of the neck property.
     * 
     */
    public void setNeck(double value) {
        this.neck = value;
    }

    /**
     * Gets the value of the height property.
     * 
     */
    public double getHeight() {
        return height;
    }

    /**
     * Sets the value of the height property.
     * 
     */
    public void setHeight(double value) {
        this.height = value;
    }

    /**
     * Gets the value of the hip property.
     * 
     */
    public double getHip() {
        return hip;
    }

    /**
     * Sets the value of the hip property.
     * 
     */
    public void setHip(double value) {
        this.hip = value;
    }

    /**
     * Gets the value of the gender property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the value of the gender property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGender(String value) {
        this.gender = value;
    }

}
