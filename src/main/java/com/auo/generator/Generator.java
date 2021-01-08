package com.auo.generator;

import com.auo.generator.template.IndexPage;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.output.FileOutput;
import gg.jte.resolve.DirectoryCodeResolver;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class Generator {
    public static void main(String[] args) throws IOException {
        Loader loader = new Loader(Path.of("days").toFile());
        List<Game> games = loader.load();

        TemplateEngine templateEngine = TemplateEngine.create(
                new DirectoryCodeResolver(Path.of("src", "main", "jte")),
                ContentType.Html);

        FileOutput output = new FileOutput(Path.of("build", "index.html"));

        templateEngine.render("index.jte", new IndexPage(games), output);
        output.close();
    }
}
