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
        Loader loader = new Loader((args.length > 0 ?
                Path.of(args[0]) :
                Path.of("days")).toFile());

        List<Game> games = loader.load();

        TemplateEngine templateEngine = args.length == 0 ?
                TemplateEngine.create(new DirectoryCodeResolver(Path.of("src", "main", "jte")), ContentType.Html) :
                TemplateEngine.createPrecompiled(ContentType.Html);

        try (FileOutput output = new FileOutput(args.length > 1 ?
                Path.of(args[1], "index.html") :
                Path.of("build", "index.html"))) {

            templateEngine.render("index.jte", new IndexPage(games), output);
        }
    }
}
