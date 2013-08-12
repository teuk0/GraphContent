package com.smartcontent.graphstore.impl;


import org.neo4j.graphdb.RelationshipType;

public enum Relations implements RelationshipType{
                TAXONS_REF,
                USERS_REF,
                DOCS_REF,
                COMMUNITY_REF,
                GROUPE_REF,
                TAXON,
                USER,
                DOC,
                COMMUNITY,
                GROUPE,
	        LIKES,
	        COMMENT,
	        KNOWS,
	        ABOUT,
	        BELONGSTO,
	        HAVEREAD,
	        CONTRIBUTE,
	        TRANSLATE,
                CONTAINS,
                KWREL
}
