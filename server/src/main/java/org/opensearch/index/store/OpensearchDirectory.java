/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */

package org.opensearch.index.store;

import org.apache.lucene.store.*;
import org.opensearch.core.index.shard.ShardId;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

/**
 * A {@link Directory} implementation that wraps another directory and associates
 * it with a {@link ShardId}. This class delegates all directory operations to
 * the wrapped instance while maintaining a reference to the shard it belongs to.
 * <p>
 * This implementation is useful for tracking which shard a directory belongs to,
 * particularly in scenarios where multiple shards might be operating in the same
 * process.
 */
public class OpensearchDirectory extends Directory {

    private final Directory delegate;
    private final ShardId shardId;

    /**
     * Creates a new OpensearchDirectory.
     *
     * @param delegate The underlying directory to delegate operations to
     * @param shardId The shard identifier associated with this directory
     */
    public OpensearchDirectory(Directory delegate, ShardId shardId) {
        this.delegate = delegate;
        this.shardId = shardId;
    }

    /**
     * Returns the shard identifier associated with this directory.
     *
     * @return the shard identifier
     */
    public ShardId shardId() {
        return shardId;
    }

    @Override
    public String[] listAll() throws IOException {
        return delegate.listAll();
    }

    @Override
    public void deleteFile(String name) throws IOException {
        delegate.deleteFile(name);
    }

    @Override
    public long fileLength(String name) throws IOException {
        return delegate.fileLength(name);
    }

    @Override
    public IndexOutput createOutput(String name, IOContext context) throws IOException {
        return delegate.createOutput(name, context);
    }

    @Override
    public IndexOutput createTempOutput(String prefix, String suffix, IOContext context) throws IOException {
        return delegate.createTempOutput(prefix, suffix, context);
    }

    @Override
    public void sync(Collection<String> names) throws IOException {
        delegate.sync(names);
    }

    @Override
    public void syncMetaData() throws IOException {
        delegate.syncMetaData();
    }

    @Override
    public void rename(String source, String dest) throws IOException {
        delegate.rename(source, dest);
    }

    @Override
    public IndexInput openInput(String name, IOContext context) throws IOException {
        return delegate.openInput(name, context);
    }

    @Override
    public Lock obtainLock(String name) throws IOException {
        return delegate.obtainLock(name);
    }

    @Override
    public void close() throws IOException {
        delegate.close();
    }

    @Override
    public Set<String> getPendingDeletions() throws IOException {
        return delegate.getPendingDeletions();
    }
}
