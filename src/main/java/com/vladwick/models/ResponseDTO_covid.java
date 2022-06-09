package com.vladwick.models;

import lombok.Data;

@Data
public class ResponseDTO_covid {
	String coutry;
	String cases;
	String deaths;
	String recoveries;
}
