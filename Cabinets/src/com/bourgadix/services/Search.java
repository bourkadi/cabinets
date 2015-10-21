package com.bourgadix.services;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.query.dsl.QueryBuilder;

import com.bourgadix.dao.Client;
import com.bourgadix.dao.HibernateUtils;

public class Search {

	public static List<Client> search(String queryString) {
		Session session = HibernateUtils.getSessionFactory().openSession();
		FullTextSession fullTextSession = org.hibernate.search.Search.getFullTextSession(session);

		QueryBuilder queryBuilder = fullTextSession.getSearchFactory()
				.buildQueryBuilder().forEntity(Client.class).get();
		org.apache.lucene.search.Query luceneQuery = queryBuilder.keyword()
				.onFields("name", "lastname", "phone", "birthplace")
				.matching(queryString).createQuery();

		// wrap Lucene query in a javax.persistence.Query
		org.hibernate.Query fullTextQuery = fullTextSession
				.createFullTextQuery(luceneQuery, Client.class);

		List<Client> contactList = fullTextQuery.list();

		fullTextSession.close();
		return contactList;
	}
	public static void main(String[] args) {
		int i = 0;
		for (Client string : search("Reda")) {
			System.out.println(string.getName());
			i++;
		}
		System.out.println(i);
	}
	
}
