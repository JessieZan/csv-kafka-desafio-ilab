package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pedido")
public class Pedido {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "data_criacao")
	private String dataCriacao;
	@Column(name = "valor_total")
	private Double valorTotal;
	@Column(name = "endereco_entrega", columnDefinition = "TEXT")
	private String enderecoEntrega;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(String dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	public Double getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}
	public String getEnderecoEntrega() {
		return enderecoEntrega;
	}
	public void setEnderecoEntrega(String enderecoEntrega) {
		this.enderecoEntrega = enderecoEntrega;
	}
	
}
