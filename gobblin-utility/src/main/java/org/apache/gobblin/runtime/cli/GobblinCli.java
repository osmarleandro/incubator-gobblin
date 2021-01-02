/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.gobblin.runtime.cli;

import com.google.common.collect.Sets;

import org.apache.gobblin.annotation.Alias;
import org.apache.gobblin.util.ClassAliasResolver;
import org.apache.gobblin.util.IClassAliasResolver;


/**
 * Instantiates a {@link CliApplication} and runs it.
 */
public class GobblinCli {

  public static void main(String[] args) {
    IClassAliasResolver<CliApplication> resolver = new ClassAliasResolver<>(CliApplication.class);

    if (args.length < 1 || Sets.newHashSet("-h", "--help").contains(args[0])) {
      printUsage(resolver);
      return;
    }

    String alias = args[0];

    try {
      CliApplication application = resolver.resolveClass(alias).newInstance();
      application.run(args);
    } catch (ReflectiveOperationException roe) {
      System.err.println("Could not find an application with alias " + alias);
      printUsage(resolver);
      System.exit(1);
    } catch (Throwable t) {
      System.out.println("Error: " + t.getMessage());
      t.printStackTrace();
      System.exit(2);
    }
  }

  private static void printUsage(IClassAliasResolver<CliApplication> resolver) {
    System.out.println("Usage: gobblin cli <command>");
    System.out.println("Available commands:");
    for (Alias alias : resolver.getAliasObjects()) {
      System.out.println("\t" + alias.value() + "\t\t" + alias.description());
    }
  }

}
