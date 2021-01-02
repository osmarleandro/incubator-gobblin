package org.apache.gobblin.writer;

public interface IFluentDataWriterBuilder<S, D, B extends IFluentDataWriterBuilder<S, D, B>> {

	B writeTo(Destination destination);

	B writeInFormat(WriterOutputFormat format);

	DataWriterBuilder<S, D> withWriterId(String writerId);

	DataWriterBuilder<S, D> withSchema(S schema);

	DataWriterBuilder<S, D> withBranches(int branches);

	DataWriterBuilder<S, D> forBranch(int branch);

}