# Project Manager
## Description
Project Menager – web application for projects management. This application enable to manage and view structure of projects and files from Repository located in local server without need for downloading.
## Technologies
- Java:
  - Spring Framework:
    - Spring MVC: application-level on the basis on design pattern: model-view-controller
    - Spring Security:
    - Spring Boot:
  - JPA & Hibernate:
- HTML
- Thymeleaf
- CSS

## Software tools
- IntelliJ IDEA 2017.2.6
- Maven
- XAMPP [MySQL]

## Features
- Home page: ![zmt-home](https://user-images.githubusercontent.com/36785400/40976193-94a0a514-68cd-11e8-99c9-b7b2e35d5862.jpg)
- Overall review of repositories carried by company: ![zmt-all_repo](https://user-images.githubusercontent.com/36785400/40979338-d69f0804-68d5-11e8-8974-a648cba88b9b.jpg)
- Adding new Repository(Putting the Bare Repository on a Server): ![zmt-new_repo 1](https://user-images.githubusercontent.com/36785400/40982021-996cd4aa-68dc-11e8-960b-2ded57a0c501.jpg)
- Details of Repository: tags, tree file in Repository and editing descriptions: ![zmt-details_repo](https://user-images.githubusercontent.com/36785400/40982293-326aa290-68dd-11e8-8654-55e0232a6603.jpg)
- Preview of the sets content: ![zmt-all_sets](https://user-images.githubusercontent.com/36785400/40982210-fa7a2252-68dc-11e8-9a1b-7641ce8a8e2d.jpg)
- Adding or deleting existed Repository to Sets ![zmt-edit_sets](https://user-images.githubusercontent.com/36785400/40982095-bcaac116-68dc-11e8-9b90-1920fa09c213.jpg)

## Project's structure
- src:
  - main:
    - java/zmt/forum:
      - controllers:
        - IndexController.java
        - RepoController.java
        - SetController.java
      - entity:
        - Composition.java
        - Repo.java
        - Set.java
      - repository:
        - CompositionRepository.java
        - RepoRepository.java
        - SetRepository.java
      - services:
        - CompositionService.java
        - RepoService.java
        - SetService.java
      - trees:
        - Node.java
        - NoteType.enum
        - Tree.java
        - TreeType.enum
      - ManagerApplication.java
    - resources:
      - templates:
        - _links.html
        - _navigation.html
        - _treeview.html
        - contentfile.html
        - index.html
        - newrepo.html
        - newset.html
        - repodetails.html
        - repos.html
        - setdetails.html
        - sets.html
      - application.properties
  - test/java/pl/zmt/manager
    - ManagerApplicationTests.java [TODO]
- pom.xml