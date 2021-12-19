package ru.netology.servlet;

import ru.netology.controller.PostController;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
    private PostController controller;
    private final String GET_METHOD = "GET";
    private final String POST_METHOD = "POST";
    private final String DELETE_METHOD = "DELETE";
    private final String PATH_API = "/api/posts";
    private final String PATH_API_MATCHES = "/api/posts/\\d+";

    @Override
    public void init() {
        final var repository = new PostRepository();
        final var service = new PostService(repository);
        controller = new PostController(service);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        // если деплоились в root context, то достаточно этого
        try {
            final var path = req.getRequestURI();
            final var method = req.getMethod();

            // primitive routing
            if (method.equals(GET_METHOD) && path.equals(PATH_API)) {
                controller.all(resp);
                return;
            }
            if (method.equals(GET_METHOD) && path.matches(PATH_API_MATCHES)) {
                // easy way
                final var id = getParseLong(path);
                controller.getById(id, resp);
                return;
            }
            if (method.equals(POST_METHOD) && path.equals(PATH_API)) {
                controller.save(req.getReader(), resp);
                return;
            }
            if (method.equals(DELETE_METHOD) && path.matches(PATH_API_MATCHES)) {
                // easy way
                final var id = getParseLong(path);
                controller.removeById(id, resp);
                return;
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private long getParseLong(String path) {
        return Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
    }
}

