/*
 * Copyright 2014-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package guestbook;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import org.springframework.util.Assert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A guestbook entry. An entity as in the Domain Driven Design context. Mapped onto the database using JPA annotations.
 *
 * @author Paul Henke
 * @author Oliver Drotbohm
 * @see https://en.wikipedia.org/wiki/Domain-driven_design#Building_blocks
 */
@Entity
class GuestbookEntry {

	private @Id @GeneratedValue Long id;
	private String name, nachname, text;
	private final LocalDateTime date;
	private boolean isNier, running;
	private int locationX, locationY;

	/**
	 * Creates a new {@link GuestbookEntry} for the given name and text.
	 *
	 * @param name must not be {@literal null} or empty
	 * @param text must not be {@literal null} or empty
	 */
	public GuestbookEntry(String name, String text) {

		Assert.hasText(name, "Name must not be null or empty!");
		Assert.hasText(text, "Text must not be null or empty!");

		Pattern p = Pattern.compile("^([\\w\\s]*?)\\s*(\\w*)\\s*$");
		Matcher m = p.matcher(name);

		this.isNier = false;
		this.locationX = 0;
		this.locationY = 0;

		if (name.toLowerCase().matches("^yorha[\\s\\w]*(9s|2b)[\\s\\w]*$")) {
			System.out.println(name.toLowerCase().matches("^yorha[\\s\\w]*(9s|2b)[\\s\\w]*$"));
			this.isNier= true;

			this.running = false;

			while (this.running) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}

				this.locationX += 10;
				this.locationY += 10;
				if (this.locationX > 100) {
					this.running = false;
				}
			}
		}

		if (m.find() && m.group(1).length() > 0) {
			this.name = m.group(1);
			this.nachname = m.group(2);
		} else {
			this.name = name;
			this.nachname = "";
		}

		this.text = text;
		this.date = LocalDateTime.now();
		System.out.println("Name: " + this.name + " Nachname: " + this.nachname);
	}

	@SuppressWarnings("unused")
	private GuestbookEntry() {
		this.name = null;
		this.nachname = null;
		this.text = null;
		this.date = null;
		this.isNier = false;
		this.locationX = 0;
		this.locationY = 0;
	}

	public String getName() {
		return name;
	}

	public String getNachname() {
		return nachname;
	}

	public boolean getIsNier() {
		return isNier;
	}

	public Long getId() {
		return id;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public String getText() {
		return text;
	}

	public int getLocationX() {
		return locationX;
	}

	public int getLocationY() {
		return locationY;
	}
}
