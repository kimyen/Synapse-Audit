package org.sagebionetworks.audit.util;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.sagebionetworks.aws.utils.s3.ObjectCSVReader;
import org.sagebionetworks.repo.model.audit.AccessRecord;
import org.sagebionetworks.warehouse.workers.db.PagingQueryIterator;
import org.sagebionetworks.warehouse.workers.model.SnapshotHeader;
import org.sagebionetworks.warehouse.workers.utils.AccessRecordTestUtil;

public class QueryResultWriterTest {

	PagingQueryIterator<AccessRecord> mockIterator;
	List<AccessRecord> list;
	String fileName;

	@Before
	public void before() {
		mockIterator = Mockito.mock(PagingQueryIterator.class);
		list = new ArrayList<AccessRecord>();
		list.add(AccessRecordTestUtil.createValidAccessRecord());
		list.add(AccessRecordTestUtil.createValidAccessRecord());
		fileName = "testFile";
	}

	@Test
	public void test() throws IOException {
		Mockito.when(mockIterator.hasNext()).thenReturn(true, true, false);
		Mockito.when(mockIterator.next()).thenReturn(list.get(0), list.get(1));
		File file = null;
		ObjectCSVReader<AccessRecord> reader = null;
		try {
			file = QueryResultWriter.writeQueryResultToFile(mockIterator, fileName, SnapshotHeader.ACCESS_RECORD_HEADERS, AccessRecord.class);
			reader = new ObjectCSVReader<AccessRecord>(new FileReader(file), AccessRecord.class, SnapshotHeader.ACCESS_RECORD_HEADERS);
			List<AccessRecord> actual = new ArrayList<AccessRecord>();
			AccessRecord record = null;
			while ((record = reader.next()) != null) {
				actual.add(record);
			}
			assertEquals(list, actual);
		} finally {
			if (reader != null) reader.close();
			if (file != null) file.delete();
		}
	}

}
