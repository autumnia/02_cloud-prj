package com.autumnia.batch.demo.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.autumnia.batch.demo.listener.DemoJobExecutionListener;
import com.autumnia.batch.demo.listener.DemoStepExcecutionListener;
import com.autumnia.batch.demo.model.Product;
import com.autumnia.batch.demo.processor.DemoInMemeItemProcessor;
import com.autumnia.batch.demo.reader.DemoInMemReader;
import com.autumnia.batch.demo.writer.DemoConsoleItemWriter;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
	private final Environment env;
	
	private final JobBuilderFactory jobs;
	private final StepBuilderFactory steps;
	private final DemoJobExecutionListener jobListener;
	private final DemoStepExcecutionListener stepListener;
	
	private final DemoInMemReader demoInMemReader;
	private final DemoInMemeItemProcessor demoInMemeItemProcessor;
	private final DemoConsoleItemWriter DemoConsoleItemWriter;

	@Autowired
	public BatchConfiguration(Environment env, 
			JobBuilderFactory jobs, StepBuilderFactory steps, 
			DemoJobExecutionListener jobListener, DemoStepExcecutionListener stepListener,
			DemoInMemReader demoInMemReader, DemoInMemeItemProcessor demoInMemeItemProcessor, DemoConsoleItemWriter demoConsoleItemWriter ) {
		this.env = env;
		
		this.jobs = jobs;
		this.steps = steps;
		this.jobListener = jobListener;
		this.stepListener = stepListener;
		
		this.demoInMemReader = demoInMemReader;
		this.demoInMemeItemProcessor = demoInMemeItemProcessor;
		this.DemoConsoleItemWriter = demoConsoleItemWriter;
	}
	
	@Bean
	@StepScope	
	public StaxEventItemReader xmlItemReader( @Value ( "#{jobParameters['fileInput']}"	)  FileSystemResource fileInput ) {
		StaxEventItemReader reader = new StaxEventItemReader();
		reader.setResource( fileInput );
		
		reader.setFragmentRootElementName("product");
		
		reader.setUnmarshaller( new Jaxb2Marshaller() {
			{
				setClassesToBeBound(Product.class);
			}
		});
		
		return reader;
	}
	
	
	@Bean
	@StepScope
	public FlatFileItemReader flatFileItemReader(@Value ( "#{jobParameters['fileInput']}"	)  FileSystemResource fileInput ){
	//public FlatFileItemReader flatFileItemReader(){
		FlatFileItemReader reader = new FlatFileItemReader();
	
		// step 1 let reader know where is the file
		reader.setResource( fileInput );
		//reader.setResource( new FileSystemResource("inputdata/product.log") );
	
		//create the line Mapper
		reader.setLineMapper(
			new DefaultLineMapper<Product>(){
				{
					setLineTokenizer( new DelimitedLineTokenizer() {
						{
							setNames( new String[]{"prodId","productName","prodDesc","price","unit"});
							setDelimiter("|");
						}
					});

					setFieldSetMapper( new BeanWrapperFieldSetMapper<Product>(){
            			{
            				setTargetType(Product.class);
            			}
            		});
				}
			}
		);
	
		//step 3 tell reader to skip the header
        reader.setLinesToSkip(1);
        return reader;
	}
	
	
	private Tasklet demoTasklet() {
		return ( new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println( "Hello World");
				return RepeatStatus.FINISHED;
			}
		});
	}	
		
	
	@Bean
	public Step step1() {
		return steps.get("step1")
				.listener(stepListener)
				.tasklet(demoTasklet())
				.build();
	}
	
	
	@Bean
	public Step step2() {
		return steps.get("step2")
				.<Integer, Integer>chunk(3)
				.reader( this.xmlItemReader(null) )
				//.reader( this.flatFileItemReader(null) )
				//.reader( this.demoInMemReader )
				//.processor(this.demoInMemeItemProcessor)
				.writer(this.DemoConsoleItemWriter)
				.build();
	}
	

	@Bean
	public Job helloworldJob() {
		return jobs.get("helloworldJob")
				.incrementer(new RunIdIncrementer())
				.listener(this.jobListener)
				.start(step1())
				.next(step2())
				.build();
	}
	

}
