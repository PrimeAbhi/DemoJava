package com.demo.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class CartItem implements Serializable {

	private static final long serialVersionUID = -189412481L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private int qty;
	private BigDecimal subtotal;

	@OneToOne
	private TShirt tshirt;

	@OneToMany(mappedBy = "cartItem")
	@JsonIgnore
	private List<TshirtToCartItem> tshirtToCartItemList;

	@ManyToOne
	@JoinColumn(name = "shopping_cart_id")
	@JsonIgnore
	private ShoppingCart shoppingCart;

	@ManyToOne
	@JoinColumn(name = "order_id")
	@JsonIgnore
	private Order order;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public TShirt getTshirt() {
		return tshirt;
	}

	public void setTshirt(TShirt tshirt) {
		this.tshirt = tshirt;
	}

	public List<TshirtToCartItem> getTshirtToCartItemList() {
		return tshirtToCartItemList;
	}

	public void setTshirtToCartItemList(List<TshirtToCartItem> tshirtToCartItemList) {
		this.tshirtToCartItemList = tshirtToCartItemList;
	}

	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

}
