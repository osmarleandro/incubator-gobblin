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
package org.apache.gobblin.converter.avro;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import org.apache.avro.generic.GenericRecord;
import org.apache.gobblin.configuration.WorkUnitState;
import org.apache.gobblin.converter.DataConversionException;


public class AvroToJsonStringConverter extends AvroToJsonStringConverterBase<String> {
  @Override
  protected String processUtf8Bytes(byte[] utf8Bytes) {
    return new String(utf8Bytes, StandardCharsets.UTF_8);
  }

@Override
public Iterable<String> convertRecord(String outputSchema, GenericRecord inputRecord, WorkUnitState workUnit) throws DataConversionException {
    try {
      byte[] utf8Bytes = this.serializer.get().serialize(inputRecord);
      return Collections.singleton(processUtf8Bytes(utf8Bytes));
    } catch (IOException ioe) {
      throw new DataConversionException(ioe);
    }
  }
}
