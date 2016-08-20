/*
 * Copyright 2016 NUROX Ltd.
 *
 * Licensed under the NUROX Ltd Software License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.looseboxes.com/legal/licenses/software.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.idisc.web.tasks;

import com.bc.html.HtmlPageGen;
import com.bc.task.AbstractStoppableTask;
import com.bc.util.XLogger;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.logging.Level;
import javax.servlet.ServletContext;

/**
 * @author Chinomso Bassey Ikwuagwu on Aug 14, 2016 2:03:14 PM
 */
public class SaveRio2016MedalsTableToNoticesDir extends AbstractStoppableTask<Path> {
    
    private final String medalsTableHtml;
    
    private final ServletContext context;

    public SaveRio2016MedalsTableToNoticesDir(
            ServletContext context, String medalsTableHtml) {
        this.medalsTableHtml = medalsTableHtml;
        this.context = context;
    }

    @Override
    public Path doCall() throws IOException {
        
        HtmlPageGen pageGen = new HtmlPageGen();
        String title = "Rio 2016 Medals Table";

        final CharSequence pageHtml = pageGen.getPage(title, title, this.medalsTableHtml);

        String output = pageHtml.toString();

        output = output.replace("<tr class=\"table-expand\">", "<tr class=\"table-expand\" style=\"visibility:collapse;\">");

        Path path = Paths.get(context.getRealPath("/notices"), "rio_2016_medals_table.html");
        Files.write(path, output.getBytes(),
                StandardOpenOption.WRITE,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);

        XLogger.getInstance().log(Level.FINE, "Successfully created and populated: {0}", this.getClass(), path);

        return path;
    }

    @Override
    public String getTaskName() {
        return this.getClass().getSimpleName();
    }
}
