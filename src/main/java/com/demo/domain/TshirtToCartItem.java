package com.demo.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class TshirtToCartItem implements Serializable {
	private static final long serialVersionUID = 879172834L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "tshirt_id")
	private TShirt tshirt;

	@ManyToOne
	@JoinColumn(name = "cart_item_id")
	private CartItem cartItem;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TShirt getTshirt() {
		return tshirt;
	}

	public void setTshirt(TShirt tshirt) {
		this.tshirt = tshirt;
	}

	public CartItem getCartItem() {
		return cartItem;
	}

	public void setCartItem(CartItem cartItem) {
		this.cartItem = cartItem;
	}

}
